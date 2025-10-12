package com.example.qrgenerator.presentation.screens.qrDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.usecase.qrGenerator.GetQrByIdUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.UpdateQrUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for QR Details screen.
 * Handles loading a QR by ID and updating it.
 */
@HiltViewModel
class QrDetailsViewModel @Inject constructor(
    private val getQrByIdUseCase: GetQrByIdUseCase,
    private val updateQrUseCase: UpdateQrUseCase
) : ViewModel() {

    // Backing StateFlow to emit UI states
    private val _uiState = MutableStateFlow<QrDetailsUIState>(QrDetailsUIState.Loading)
    val uiState: StateFlow<QrDetailsUIState> = _uiState

    /**
     * Loads a QR by its ID.
     * Updates UI state to Loading -> Success/Error.
     */
    fun loadQr(qrId: String) = viewModelScope.launch {
        _uiState.value = QrDetailsUIState.Loading
        try {
            val qr = getQrByIdUseCase(qrId)
            _uiState.value = QrDetailsUIState.Success(qr)
        } catch (e: Exception) {
            _uiState.value = QrDetailsUIState.Error(e.message ?: "Could not load QR")
        }
    }

    /**
     * Updates the QR data.
     * Shows Loading while updating and then Success/Error.
     */
    fun updateQr(qr: QrDomain) = viewModelScope.launch {
        _uiState.value = QrDetailsUIState.Loading
        try {
            updateQrUseCase(qr)
            _uiState.value = QrDetailsUIState.Success(qr)
        } catch (e: Exception) {
            _uiState.value = QrDetailsUIState.Error(e.message ?: "Update failed")
        }
    }
}