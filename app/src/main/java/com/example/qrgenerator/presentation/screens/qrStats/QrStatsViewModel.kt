package com.example.qrgenerator.presentation.screens.qrStats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.usecase.stadistic.GetQrScansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for loading and managing QR scan statistics.
 */
@HiltViewModel
class QrStatsViewModel @Inject constructor(
    private val getQrScansUseCase: GetQrScansUseCase
) : ViewModel() {

    /** Backing StateFlow for the UI state of the statistics screen. */
    private val _uiState = MutableStateFlow<QrStatsUIState>(QrStatsUIState.Loading)

    /** Publicly exposed UI state as a read-only StateFlow. */
    val uiState: StateFlow<QrStatsUIState> = _uiState

    /**
     * Loads the scans for a given QR code ID.
     * Updates [_uiState] with Loading, Success, or Error.
     *
     * @param qrId The ID of the QR code whose scans will be fetched.
     */
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