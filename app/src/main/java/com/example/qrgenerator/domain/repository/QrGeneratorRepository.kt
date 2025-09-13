package com.example.qrgenerator.domain.repository

import com.example.qrgenerator.domain.model.QrDomain

interface QrGeneratorRepository {
    suspend fun getQrById(id: String): QrDomain
    suspend fun createQr(qr: QrDomain)
    suspend fun getAllQrs(): List<QrDomain>
    suspend fun updateQr(qr: QrDomain)
}