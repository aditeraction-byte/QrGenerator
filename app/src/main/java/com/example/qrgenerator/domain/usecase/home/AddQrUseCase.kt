package com.example.qrgenerator.domain.usecase.home

import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.HomeRepository

class AddQrUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(qr: QrDomain) = repository.addQr(qr)
}
