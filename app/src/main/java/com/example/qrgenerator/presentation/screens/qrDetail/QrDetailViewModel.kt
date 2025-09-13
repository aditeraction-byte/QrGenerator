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


@HiltViewModel
class QrDetailsViewModel @Inject constructor(
    private val getQrByIdUseCase: GetQrByIdUseCase,
    private val updateQrUseCase: UpdateQrUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QrDetailsUIState>(QrDetailsUIState.Loading)
    val uiState: StateFlow<QrDetailsUIState> = _uiState

    fun loadQr(qrId: String) = viewModelScope.launch {
        _uiState.value = QrDetailsUIState.Loading
        try {
            val qr = getQrByIdUseCase(qrId)
            _uiState.value = QrDetailsUIState.Success(qr)
        } catch (e: Exception) {
            _uiState.value = QrDetailsUIState.Error(e.message ?: "Could not load QR")
        }
    }

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