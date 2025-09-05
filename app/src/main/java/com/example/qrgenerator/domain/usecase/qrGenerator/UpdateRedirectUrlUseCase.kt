package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrRepository

class UpdateRedirectUrlUseCase(private val repository: QrRepository) {
    suspend operator fun invoke(id: String, redirectUrl: String): QrDomain =
        repository.updateRedirectUrl(id, redirectUrl)
}