package com.example.qrgenerator.di

import com.example.qrgenerator.data.repository.AuthRepositoryImpl
import com.example.qrgenerator.data.repository.QrRepositoryImpl
import com.example.qrgenerator.domain.repository.AuthRepository
import com.example.qrgenerator.domain.repository.QrRepository
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
    fun provideQrRepository(firestore: FirebaseFirestore): QrRepository =
        QrRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(auth)
}