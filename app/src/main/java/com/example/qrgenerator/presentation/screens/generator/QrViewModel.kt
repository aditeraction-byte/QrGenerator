package com.example.qrgenerator.presentation.screens.generator


import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.usecase.qrGenerator.CreateQrUseCase
import com.example.qrgenerator.utils.helpers.QrHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val createQrUseCase: CreateQrUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QrUIState>(QrUIState.Idle)
    val uiState: StateFlow<QrUIState> = _uiState

    fun normalizeUrl(url: String): String =
        if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url"

    fun createQr(qrId: String, title: String, redirectUrl: String) {
        viewModelScope.launch {
            if (qrId.isBlank()) {
                _uiState.value = QrUIState.Error("Shortlink cannot be empty")
                return@launch
            }

            _uiState.value = QrUIState.Loading
            try {
                val qr = QrDomain(
                    id = qrId,
                    title = title,
                    redirectUrl = normalizeUrl(redirectUrl),
                    fgColor = "#000000",
                    bgColor = "#FFFFFF"
                )

                createQrUseCase(qr)

                val bitmap = QrHelper.generateQrBitmap(
                    content = qr.shortLink,
                    fgColorHex = qr.fgColor,
                    bgColorHex = qr.bgColor
                ).asImageBitmap()

                _uiState.value = QrUIState.Success(qr, bitmap)
            } catch (e: Exception) {
                _uiState.value = QrUIState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

