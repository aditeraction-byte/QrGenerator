package com.example.qrgenerator.domain.usecase.auth

import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.AuthRepository

class GetCurrentUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): UserDomain? =
        repository.getCurrentUser()
}