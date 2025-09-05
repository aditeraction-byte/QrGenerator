package com.example.qrgenerator.di

import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.repository.QrRepository
import com.example.qrgenerator.domain.usecase.auth.GetCurrentUserUseCase
import com.example.qrgenerator.domain.usecase.auth.LoginUseCase
import com.example.qrgenerator.domain.usecase.auth.LogoutUseCase
import com.example.qrgenerator.domain.usecase.auth.RegisterUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.GetQrByIdUseCase
import com.example.qrgenerator.domain.usecase.qrGenerator.UpdateRedirectUrlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // QrRepositoy
    @Provides
    fun provideGetQrByIdUseCase(repository: QrRepository) = GetQrByIdUseCase(repository)

    @Provides
    fun provideUpdateRedirectUrlUseCase(repository: QrRepository) = UpdateRedirectUrlUseCase(repository)

    //AuthRepository
    @Provides
    fun provideLoginUseCase(repository: AuthRepository) = LoginUseCase(repository)

    @Provides
    fun provideRegisterUseCase(repository: AuthRepository) = RegisterUseCase(repository)

    @Provides
    fun provideLogoutUseCase(repository: AuthRepository) = LogoutUseCase(repository)

    @Provides
    fun provideGetCurrentUserUseCase(repository: AuthRepository) = GetCurrentUserUseCase(repository)


}