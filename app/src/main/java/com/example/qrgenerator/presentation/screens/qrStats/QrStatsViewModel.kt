package com.example.qrgenerator.presentation.screens.qrStats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.model.QrScanDomain
import com.example.qrgenerator.domain.usecase.qrGenerator.GetQrByIdUseCase
import com.example.qrgenerator.domain.usecase.stadistic.GetQrScansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QrStatsViewModel @Inject constructor(
    private val getQrScansUseCase: GetQrScansUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QrStatsUIState>(QrStatsUIState.Loading)
    val uiState: StateFlow<QrStatsUIState> = _uiState

    fun loadScans(qrId: String) = viewModelScope.launch {
        _uiState.value = QrStatsUIState.Loading
        try {
            val scans = getQrScansUseCase(qrId)
            _uiState.value = QrStatsUIState.Success(scans)
        } catch (e: Exception) {
            _uiState.value = QrStatsUIState.Error(e.message ?: "Unknown error")
        }
    }
}