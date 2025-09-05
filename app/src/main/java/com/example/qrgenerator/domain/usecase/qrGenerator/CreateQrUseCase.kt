package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.HomeRepository
import com.example.qrgenerator.domain.repository.QrGeneratorRepository

class CreateQrUseCase(
    private val generatorRepo: QrGeneratorRepository,
    private val homeRepo: HomeRepository
) {
    suspend operator fun invoke(qr: QrDomain) {
        generatorRepo.createQr(qr)
        homeRepo.addQr(qr)
    }
}