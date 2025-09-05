package com.example.qrgenerator.presentation.screens.generator

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.usecase.qrGenerator.GetQrByIdUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.UpdateRedirectUrlUseCase
import com.example.qrgenerator.utils.helpers.QrHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val getQrByIdUseCase: GetQrByIdUseCase,
    private val updateRedirectUrlUseCase: UpdateRedirectUrlUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QrUIState>(QrUIState.Loading)
    val uiState: StateFlow<QrUIState> = _uiState

    private val qrShortlink = "https://mavrial.github.io/Qr-Redirect/"
    private var qrBitmap: ImageBitmap? = null
    private val qrId = "default_qr"

    init {
        loadInitialQr()
    }

    private fun loadInitialQr() {
        viewModelScope.launch {
            _uiState.value = QrUIState.Loading
            try {
                val qrDomain = getQrByIdUseCase(qrId)
                qrBitmap = qrBitmap ?: QrHelper.generateQrBitmap(qrShortlink).asImageBitmap()
                _uiState.value = QrUIState.Success(qrDomain, qrBitmap!!)
            } catch (e: Exception) {
                _uiState.value = QrUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateRedirectUrl(newUrl: String) {
        viewModelScope.launch {
            try {

                val normalizedUrl = if (newUrl.startsWith("http://") || newUrl.startsWith("https://")) {
                    newUrl
                } else {
                    "https://$newUrl"
                }

                val updatedQr = updateRedirectUrlUseCase(qrId, normalizedUrl)
                _uiState.value = qrBitmap?.let { bitmap ->
                    QrUIState.Success(updatedQr, bitmap)
                } ?: QrUIState.Error("QR image not available")
            } catch (e: Exception) {
                _uiState.value = QrUIState.Error(e.message ?: "Unknown error")
            }
        }
    }
}