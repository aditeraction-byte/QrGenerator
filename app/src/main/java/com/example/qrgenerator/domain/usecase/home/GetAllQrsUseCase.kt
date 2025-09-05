package com.example.qrgenerator.domain.usecase.home

import com.example.qrgenerator.domain.repository.HomeRepository

class GetAllQrsUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() = repository.getAllQrs()
}
