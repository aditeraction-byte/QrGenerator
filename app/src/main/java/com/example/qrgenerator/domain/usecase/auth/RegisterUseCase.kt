package com.example.qrgenerator.domain.usecase.auth

import com.example.qrgenerator.domain.model.UserDomain
import com.example.qrgenerator.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): UserDomain =
        repository.register(email, password).getOrThrow()
}