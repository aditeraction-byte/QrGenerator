package com.example.qrgenerator.domain.repository

import com.example.qrgenerator.domain.model.QrDomain

/**
 * Repository for home-related operations, mainly QR CRUD.
 */
interface HomeRepository {
    suspend fun getAllQrs(): List<QrDomain>
    suspend fun addQr(qr: QrDomain)
    suspend fun deleteQr(qrId: String)
}