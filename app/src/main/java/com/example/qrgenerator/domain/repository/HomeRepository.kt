package com.example.qrgenerator.domain.repository

import com.example.qrgenerator.domain.model.QrDomain

interface HomeRepository {
    suspend fun getAllQrs(): List<QrDomain>
    suspend fun addQr(qr: QrDomain)
    suspend fun deleteQr(qrId: String)
}