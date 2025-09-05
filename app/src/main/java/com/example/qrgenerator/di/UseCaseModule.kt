package com.example.qrgenerator.di

import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.repository.HomeRepository
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
import com.example.qrgenerator.domain.usecase.auth.GetCurrentUserUseCase
import com.example.qrgenerator.domain.usecase.auth.LoginUseCase
import com.example.qrgenerator.domain.usecase.auth.LogoutUseCase
import com.example.qrgenerator.domain.usecase.auth.RegisterUseCase
import com.example.qrgenerator.domain.usecase.home.AddQrUseCase
import com.example.qrgenerator.domain.usecase.home.GetAllQrsUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.CreateQrUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.GetAllGeneratorQrsUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.GetQrByIdUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.UpdateQrUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // QrGenerator Use Cases
    @Provides
    fun provideGetQrByIdUseCase(repository: QrGeneratorRepository) =
        GetQrByIdUseCase(repository)

    @Provides
    fun provideCreateQrUseCase(
        generatorRepo: QrGeneratorRepository,
        homeRepo: HomeRepository
    ) = CreateQrUseCase(generatorRepo, homeRepo)

    @Provides
    fun provideUpdateQrUseCase(repository: QrGeneratorRepository) =
        UpdateQrUseCase(repository)

    @Provides
    fun provideGetAllGeneratorQrsUseCase(repository: QrGeneratorRepository) =
        GetAllGeneratorQrsUseCase(repository)

    // Auth Use Cases
    @Provides
    fun provideLoginUseCase(repository: AuthRepository) =
        LoginUseCase(repository)

    @Provides
    fun provideRegisterUseCase(repository: AuthRepository) =
        RegisterUseCase(repository)

    @Provides
    fun provideLogoutUseCase(repository: AuthRepository) =
        LogoutUseCase(repository)

    @Provides
    fun provideGetCurrentUserUseCase(repository: AuthRepository) =
        GetCurrentUserUseCase(repository)

    // Home Use Cases
    @Provides
    fun provideGetAllQrsUseCase(repo: HomeRepository) =
        GetAllQrsUseCase(repo)

    @Provides
    fun provideAddQrUseCase(repo: HomeRepository) =
        AddQrUseCase(repo)
}