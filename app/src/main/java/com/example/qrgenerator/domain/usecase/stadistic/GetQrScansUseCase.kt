package com.example.qrgenerator.domain.usecase.stadistic

import com.example.qrgenerator.domain.model.QrScanDomain
import com.example.qrgenerator.domain.repository.QrScanRepository
import javax.inject.Inject
/**
 * Use case to get all scans for a specific QR code.
 */
class GetQrScansUseCase @Inject constructor(private val repository: QrScanRepository) {
    suspend operator fun invoke(qrId: String): List<QrScanDomain> = repository.getScansForQr(qrId)
}