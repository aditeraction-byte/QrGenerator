package com.example.qrgenerator.domain.usecase.stadistic

import com.example.qrgenerator.domain.model.QrScanDomain
import com.example.qrgenerator.domain.repository.QrScanRepository
import javax.inject.Inject

class GetQrScansUseCase @Inject constructor(private val repository: QrScanRepository) {
    suspend operator fun invoke(qrId: String): List<QrScanDomain> = repository.getScansForQr(qrId)
}