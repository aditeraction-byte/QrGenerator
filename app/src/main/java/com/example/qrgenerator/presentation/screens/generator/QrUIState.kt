package com.example.qrgenerator.presentation.screens.generator

import androidx.compose.ui.graphics.ImageBitmap
import com.example.qrgenerator.domain.model.QrDomain

/**
 * Represents the UI state for the QR screen.
 */
sealed class QrUIState {
    object Idle : QrUIState()
    object Loading : QrUIState()
    data class Success(val qr: QrDomain, val bitmap: ImageBitmap) : QrUIState()
    data class Error(val message: String) : QrUIState()
}