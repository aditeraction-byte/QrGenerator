package com.example.qrgenerator.presentation.screens.qrStats

import com.example.qrgenerator.domain.model.QrScanDomain

sealed class QrStatsUIState {
    object Loading : QrStatsUIState()
    data class Success(val scans: List<QrScanDomain>) : QrStatsUIState()
    data class Error(val message: String) : QrStatsUIState()
}