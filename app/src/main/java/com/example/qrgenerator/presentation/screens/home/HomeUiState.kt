package com.example.qrgenerator.presentation.screens.home

import com.example.qrgenerator.domain.model.QrDomain

sealed class HomeUIState {
    object Loading : HomeUIState()
    data class Success(val qrList: List<QrDomain>) : HomeUIState()
    data class Error(val message: String) : HomeUIState()
}