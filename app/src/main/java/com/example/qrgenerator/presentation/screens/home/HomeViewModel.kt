package com.example.qrgenerator.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.usecase.home.GetAllQrsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllQrs: GetAllQrsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState

    init { loadQrs() }

    fun loadQrs() = viewModelScope.launch {
        _uiState.value = HomeUIState.Loading
        try {
            val list = getAllQrs()
            _uiState.value = HomeUIState.Success(list)
        } catch (e: Exception) {
            _uiState.value = HomeUIState.Error(e.message ?: "Unknown error")
        }
    }
}