package com.example.qrgenerator.domain.usecase.home

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.HomeRepository
/**
 * Use case to add a QR code.
 */
class AddQrUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(qr: QrDomain) = repository.addQr(qr)
}
