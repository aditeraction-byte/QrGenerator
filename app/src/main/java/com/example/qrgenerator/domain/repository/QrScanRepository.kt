package com.example.qrgenerator.domain.repository

import com.example.qrgenerator.domain.model.QrScanDomain

/**
 * Repository for QR scan operations.
 */
interface QrScanRepository {
    suspend fun getScansForQr(qrId: String): List<QrScanDomain>
    suspend fun addScan(scan: QrScanDomain)
}