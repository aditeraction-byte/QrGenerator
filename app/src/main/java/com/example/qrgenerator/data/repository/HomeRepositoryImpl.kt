package com.example.qrgenerator.data.repository

import android.util.Log
import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.mapper.toDto
import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.HomeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : HomeRepository {

    override suspend fun getAllQrs(): List<QrDomain> {
        val snap = firestore.collection("home_qrs").get().await()
        Log.d("HomeRepo", "Docs raw: ${snap.documents.map { it.data }}")
        val list = snap.documents.mapNotNull {
            it.toObject(QrDto::class.java)?.toDomain()
        }
        Log.d("HomeRepo", "Mapped list: $list")
        return list
    }

    override suspend fun addQr(qr: QrDomain) {
        firestore.collection("home_qrs").document(qr.id).set(qr.toDto()).await()
    }
}