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
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()
}