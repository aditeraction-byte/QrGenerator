package com.example.qrgenerator.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.usecase.auth.GetCurrentUserUseCase
import com.example.qrgenerator.domain.usecase.auth.LoginUseCase
import com.example.qrgenerator.domain.usecase.auth.LogoutUseCase
import com.example.qrgenerator.domain.usecase.auth.RegisterAndCreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerAndCreateUserUseCase: RegisterAndCreateUserUseCase, // <-- acá
    private val logoutUseCase: LogoutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val uiState: StateFlow<LoginUIState> = _uiState

    private fun handleResult(result: suspend () -> UserDomain?) = viewModelScope.launch {
        _uiState.value = LoginUIState.Loading
        try {
            val user = result()
            _uiState.value = if (user != null) LoginUIState.Success(user) else LoginUIState.LoggedOut
        } catch (e: Exception) {
            _uiState.value = LoginUIState.Error(e.message ?: "Unknown error")
        }
    }

    fun login(email: String, password: String) {
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()
        if (cleanEmail.isEmpty() || cleanPassword.isEmpty()) {
            _uiState.value = LoginUIState.Error("Email and password cannot be empty")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
            _uiState.value = LoginUIState.Error("Invalid email")
            return
        }
        handleResult { loginUseCase(cleanEmail, cleanPassword) }
    }

    fun register(email: String, password: String) {
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()
        if (cleanEmail.isEmpty() || cleanPassword.isEmpty()) {
            _uiState.value = LoginUIState.Error("Email and password cannot be empty")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
            _uiState.value = LoginUIState.Error("Invalid email")
            return
        }
        if (cleanPassword.length < 6) {
            _uiState.value = LoginUIState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUIState.Loading
            registerAndCreateUserUseCase(cleanEmail, cleanPassword).fold(
                onSuccess = { user ->
                    _uiState.value = LoginUIState.Success(user)
                },
                onFailure = { e ->
                    _uiState.value = LoginUIState.Error(e.message ?: "Registration failed")
                }
            )
        }
    }

    fun logout() = viewModelScope.launch {
        try {
            logoutUseCase()
            _uiState.value = LoginUIState.LoggedOut
        } catch (e: Exception) {
            _uiState.value = LoginUIState.Error(e.message ?: "Unknown error")
        }
    }

    fun getCurrentUser() = handleResult { getCurrentUserUseCase() }
}