package com.example.qrgenerator.presentation.screens.qrStats

import com.example.qrgenerator.domain.model.QrScanDomain
/**
 * Represents the UI state for the QR statistics screen.
 */
sealed class QrStatsUIState {
    object Loading : QrStatsUIState()
    data class Success(val scans: List<QrScanDomain>) : QrStatsUIState()
    data class Error(val message: String) : QrStatsUIState()
}