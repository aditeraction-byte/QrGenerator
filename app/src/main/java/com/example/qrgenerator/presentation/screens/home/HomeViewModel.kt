package com.example.qrgenerator.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.usecase.home.DeleteQrUseCase
import com.example.qrgenerator.domain.usecase.home.GetAllQrsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllQrsUseCase: GetAllQrsUseCase,
    private val deleteQrUseCase: DeleteQrUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        loadQrs()
    }

    fun loadQrs() = viewModelScope.launch {
        _uiState.value = HomeUIState.Loading
        try {
            val qrList = getAllQrsUseCase()
            _uiState.value = HomeUIState.Success(qrList)
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Unknown error")
        }
    }

    fun deleteQr(qrId: String) = viewModelScope.launch {
        try {
            deleteQrUseCase(qrId)
            loadQrs()
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Delete failed")
        }
    }
}