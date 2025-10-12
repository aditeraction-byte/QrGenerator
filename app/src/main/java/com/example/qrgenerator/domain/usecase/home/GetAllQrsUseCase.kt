package com.example.qrgenerator.domain.usecase.home

import com.example.qrgenerator.domain.repository.HomeRepository
/**
 * Use case to get all QR codes from home repository.
 */
class GetAllQrsUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.getAllQrs()
}
