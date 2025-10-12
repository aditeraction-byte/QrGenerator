package com.example.qrgenerator.domain.usecase.qrGenerator

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
import javax.inject.Inject
/**
 * Use case to update a QR code.
 */
class UpdateQrUseCase @Inject constructor(
    private val repository: QrGeneratorRepository
) {
    suspend operator fun invoke(qr: QrDomain) {
        repository.updateQr(qr)
    }
}