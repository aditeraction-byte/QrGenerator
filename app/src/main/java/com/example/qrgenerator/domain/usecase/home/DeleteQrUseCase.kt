package com.example.qrgenerator.domain.usecase.home

import com.example.qrgenerator.domain.repository.HomeRepository
import javax.inject.Inject
/**
 * Use case to delete a QR code.
 */
class DeleteQrUseCase @Inject constructor(
    private val repo: HomeRepository
) {
    suspend operator fun invoke(qrId: String) = repo.deleteQr(qrId)
}
