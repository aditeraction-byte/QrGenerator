package com.example.qrgenerator.domain.usecase.home

import com.example.qrgenerator.domain.repository.HomeRepository
import javax.inject.Inject

class DeleteQrUseCase @Inject constructor(
    private val repo: HomeRepository
) {
    suspend operator fun invoke(qrId: String) = repo.deleteQr(qrId)
}
