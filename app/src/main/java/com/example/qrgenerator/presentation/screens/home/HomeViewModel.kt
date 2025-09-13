package com.example.qrgenerator.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.usecase.home.DeleteQrUseCase
import com.example.qrgenerator.domain.usecase.home.GetAllQrsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllQrsUseCase: GetAllQrsUseCase,
    private val deleteQrUseCase: DeleteQrUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState

    private val _user = MutableStateFlow<UserDomain?>(null)
    val user: StateFlow<UserDomain?> = _user

    init {
        loadCurrentUser()
        loadQrs()
    }

    private fun loadCurrentUser() = viewModelScope.launch {
        _user.value = authRepository.getCurrentUser()
    }

    fun logout(onComplete: () -> Unit = {}) = viewModelScope.launch {
        authRepository.logout()
        onComplete()
    }

    fun loadQrs() = viewModelScope.launch {
        _uiState.value = HomeUIState.Loading
        try {
            val qrList = getAllQrsUseCase()
            _uiState.value = HomeUIState.Success(qrList)
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Unknown error")
        }
    }

    fun deleteQr(qrId: String) = viewModelScope.launch {
        try {
            deleteQrUseCase(qrId)
            loadQrs()
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Delete failed")
        }
    }
}