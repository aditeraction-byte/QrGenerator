package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrGeneratorRepository

class GetAllGeneratorQrsUseCase(private val repository: QrGeneratorRepository) {
    suspend operator fun invoke(): List<QrDomain> = repository.getAllQrs()
}