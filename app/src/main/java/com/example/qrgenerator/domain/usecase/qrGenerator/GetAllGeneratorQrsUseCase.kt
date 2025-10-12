package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
/**
 * Use case to get all QR codes from generator repository.
 */
class GetAllGeneratorQrsUseCase(private val repository: QrGeneratorRepository) {
    suspend operator fun invoke(): List<QrDomain> = repository.getAllQrs()
}