package com.example.qrgenerator.data.repository

import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.mapper.toDto
import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QrGeneratorRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : QrGeneratorRepository {

    override suspend fun getQrById(id: String): QrDomain {
        val doc = firestore.collection("generator_qrs").document(id).get().await()
        return doc.toObject(QrDto::class.java)?.toDomain()
            ?: throw Exception("QR not found")
    }

    override suspend fun createQr(qr: QrDomain) {
        firestore.collection("generator_qrs").document(qr.id).set(qr.toDto()).await()
    }

    override suspend fun updateQr(qr: QrDomain): QrDomain {
        firestore.collection("generator_qrs").document(qr.id).set(qr.toDto()).await()
        return qr
    }

    override suspend fun getAllQrs(): List<QrDomain> {
        val snap = firestore.collection("generator_qrs").get().await()
        return snap.documents.mapNotNull { it.toObject(QrDto::class.java)?.toDomain() }
    }
}