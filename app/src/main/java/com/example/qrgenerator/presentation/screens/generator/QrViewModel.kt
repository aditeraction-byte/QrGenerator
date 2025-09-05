package com.example.qrgenerator.presentation.screens.generator

import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.usecase.qrGenerator.CreateQrUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.GetQrByIdUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.UpdateQrUseCase
import com.example.qrgenerator.utils.helpers.QrHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val getQrByIdUseCase: GetQrByIdUseCase,
    private val createQrUseCase: CreateQrUseCase,
    private val updateQrUseCase: UpdateQrUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<QrUIState>(QrUIState.Loading)
    val uiState: StateFlow<QrUIState> = _uiState

    var currentQrId: String? = null
        private set

    fun loadQr(qrId: String) = viewModelScope.launch {
        Log.d("QrViewModel", "Loading QR $qrId")
        _uiState.value = QrUIState.Loading
        currentQrId = qrId
        try {
            val qr = getQrByIdUseCase(qrId)
            val bmp = QrHelper.generateQrBitmap(qr.shortLink, qr.fgColor, qr.bgColor).asImageBitmap()
            _uiState.value = QrUIState.Success(qr, bmp)
            Log.d("QrViewModel", "QR loaded: ${qr.id}")
        } catch (e: Exception) {
            _uiState.value = QrUIState.Error(e.message ?: "Unknown error")
            Log.e("QrViewModel", "Error loading QR", e)
        }
    }

    fun setNewQrState() {
        val newQr = QrDomain(
            id = "", title = "", redirectUrl = "",
            fgColor = "#000000", bgColor = "#FFFFFF",
            expiresAt = null
        )
        val bmp = QrHelper.generateQrBitmap(newQr.shortLink, newQr.fgColor, newQr.bgColor).asImageBitmap()
        _uiState.value = QrUIState.Success(newQr, bmp)
    }

    fun createQr(
        title: String,
        redirectUrl: String,
        fgColor: String = "#000000",
        bgColor: String = "#FFFFFF",
        expiresAt: Long? = null
    ) = viewModelScope.launch {
        try {
            val newQr = QrDomain(
                id = UUID.randomUUID().toString(),
                title = title,
                redirectUrl = normalizeUrl(redirectUrl), // Normalizamos la URL
                fgColor = fgColor,
                bgColor = bgColor,
                expiresAt = expiresAt
            )
            createQrUseCase(newQr)
            currentQrId = newQr.id
            val bmp = QrHelper.generateQrBitmap(newQr.shortLink, fgColor, bgColor).asImageBitmap()
            _uiState.value = QrUIState.Success(newQr, bmp)
        } catch (e: Exception) {
            _uiState.value = QrUIState.Error(e.message ?: "Unknown error")
        }
    }


    fun updateQr(
        title: String,
        redirectUrl: String,
        fgColor: String = "#000000",
        bgColor: String = "#FFFFFF",
        expiresAt: Long? = null
    ) = viewModelScope.launch {
        val qrId = currentQrId ?: return@launch
        try {
            val updatedQr = QrDomain(
                id = qrId,
                title = title,
                redirectUrl = normalizeUrl(redirectUrl),
                fgColor = fgColor,
                bgColor = bgColor,
                expiresAt = expiresAt
            )
            updateQrUseCase(updatedQr)
            val bmp = QrHelper.generateQrBitmap(updatedQr.shortLink, fgColor, bgColor).asImageBitmap()
            _uiState.value = QrUIState.Success(updatedQr, bmp)
        } catch (e: Exception) {
            _uiState.value = QrUIState.Error(e.message ?: "Unknown error")
        }
    }


    private fun normalizeUrl(url: String): String {
        return if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url"
    }
}