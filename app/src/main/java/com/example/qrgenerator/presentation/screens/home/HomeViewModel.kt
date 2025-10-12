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

/**
 * ViewModel responsible for managing the Home screen state.
 *
 * Handles loading the current user, fetching all QR codes,
 * deleting QR codes, and performing logout.
 *
 * @property getAllQrsUseCase Use case to fetch all QR codes for the current user.
 * @property deleteQrUseCase Use case to delete a specific QR code.
 * @property authRepository Repository for user authentication operations.
 */
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

    /** Loads the currently authenticated user into [_user] */
    private fun loadCurrentUser() = viewModelScope.launch {
        _user.value = authRepository.getCurrentUser()
    }

    /**
     * Logs out the current user and invokes [onComplete] after finishing.
     *
     * @param onComplete Optional callback to run after logout.
     */
    fun logout(onComplete: () -> Unit = {}) = viewModelScope.launch {
        authRepository.logout()
        onComplete()
    }

    /** Loads the list of QR codes and updates [_uiState] accordingly */
    fun loadQrs() = viewModelScope.launch {
        _uiState.value = HomeUIState.Loading
        try {
            val qrList = getAllQrsUseCase()
            _uiState.value = HomeUIState.Success(qrList)
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Unknown error")
        }
    }

    /**
     * Deletes a QR code by its ID and refreshes the list.
     *
     * @param qrId The ID of the QR code to delete.
     */
    fun deleteQr(qrId: String) = viewModelScope.launch {
        try {
            deleteQrUseCase(qrId)
            loadQrs()
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Delete failed")
        }
    }
}