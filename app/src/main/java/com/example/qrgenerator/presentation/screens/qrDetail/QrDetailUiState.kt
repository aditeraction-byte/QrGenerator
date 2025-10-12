package com.example.qrgenerator.presentation.screens.qrDetail

import com.example.qrgenerator.domain.model.QrDomain
/**
 * UI states for the QR Details screen.
 */
sealed class QrDetailsUIState {
    object Loading : QrDetailsUIState()
    data class Success(val qr: QrDomain) : QrDetailsUIState()
    data class Error(val message: String) : QrDetailsUIState()
}