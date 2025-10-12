package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.repository.QrGeneratorRepository
/**
 * Use case to get a QR code by its ID.
 */
class GetQrByIdUseCase(private val repository: QrGeneratorRepository) {
    suspend operator fun invoke(id: String) = repository.getQrById(id)
}