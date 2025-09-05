package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrGeneratorRepository

class UpdateQrUseCase(private val repository: QrGeneratorRepository) {
    suspend operator fun invoke(qr: QrDomain): QrDomain = repository.updateQr(qr)
}