package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.repository.QrGeneratorRepository

class GetQrByIdUseCase(private val repository: QrGeneratorRepository) {
    suspend operator fun invoke(id: String) = repository.getQrById(id)
}