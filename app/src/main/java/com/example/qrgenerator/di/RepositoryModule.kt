package com.example.qrgenerator.di

import com.example.qrgenerator.data.repository.AuthRepositoryImpl
import com.example.qrgenerator.data.repository.HomeRepositoryImpl
import com.example.qrgenerator.data.repository.QrGeneratorRepositoryImpl
import com.example.qrgenerator.data.repository.QrScanRepositoryImpl
import com.example.qrgenerator.data.repository.UserRepositoryImpl
import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.repository.HomeRepository
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
import com.example.qrgenerator.domain.repository.QrScanRepository
import com.example.qrgenerator.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module that provides repository implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides a singleton QrGeneratorRepository implementation.
     */
    @Provides
    @Singleton
    fun provideQrGeneratorRepo(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): QrGeneratorRepository = QrGeneratorRepositoryImpl(firestore, auth)

    /**
     * Provides a singleton HomeRepository implementation.
     */
    @Provides
    @Singleton
    fun provideHomeRepo(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): HomeRepository = HomeRepositoryImpl(firestore, auth)

    /**
     * Provides a singleton AuthRepository implementation.
     */
    @Provides
    @Singleton
    fun provideAuthRepo(auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(auth)

    /**
     * Provides a singleton UserRepository implementation.
     */
    @Provides
    @Singleton
    fun provideUserRepo(firestore: FirebaseFirestore): UserRepository =
        UserRepositoryImpl(firestore)

    /**
     * Provides a singleton QrScanRepository implementation.
     */
    @Provides
    @Singleton
    fun provideQrScanRepo(
        firestore: FirebaseFirestore,
    ): QrScanRepository = QrScanRepositoryImpl(firestore)
}