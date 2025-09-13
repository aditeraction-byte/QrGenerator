package com.example.qrgenerator.di

import com.example.qrgenerator.data.repository.AuthRepositoryImpl
import com.example.qrgenerator.data.repository.HomeRepositoryImpl
import com.example.qrgenerator.data.repository.QrGeneratorRepositoryImpl
import com.example.qrgenerator.data.repository.UserRepositoryImpl
import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.repository.HomeRepository
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
import com.example.qrgenerator.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQrGeneratorRepo(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): QrGeneratorRepository = QrGeneratorRepositoryImpl(firestore, auth)

    @Provides
    @Singleton
    fun provideHomeRepo(firestore: FirebaseFirestore, auth: FirebaseAuth): HomeRepository =
        HomeRepositoryImpl(firestore, auth)

    @Provides
    @Singleton
    fun provideAuthRepo(auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideUserRepo(firestore: FirebaseFirestore): UserRepository =
        UserRepositoryImpl(firestore)
}