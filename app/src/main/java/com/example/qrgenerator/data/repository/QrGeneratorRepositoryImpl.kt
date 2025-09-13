package com.example.qrgenerator.data.repository

import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.mapper.toDto
import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.QrGeneratorRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QrGeneratorRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : QrGeneratorRepository {

    private fun userQrsPath() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?: throw Exception("User not logged in"))
            .collection("home_qrs")

    override suspend fun getQrById(id: String): QrDomain {
        val doc = userQrsPath().document(id).get().await()
        return doc.toObject(QrDto::class.java)?.toDomain() ?: throw Exception("QR not found")
    }

    override suspend fun createQr(qr: QrDomain) {
        val docRef = userQrsPath().document(qr.id)
        val snapshot = docRef.get().await()

        if (snapshot.exists()) {
            throw Exception("A QR with this Shortlink already exists")
        }

        docRef.set(qr.toDto()).await()
    }

    override suspend fun updateQr(qr: QrDomain): QrDomain {
        userQrsPath().document(qr.id).set(qr.toDto()).await()
        return qr
    }

    override suspend fun getAllQrs(): List<QrDomain> {
        val snap = userQrsPath().get().await()
        return snap.documents.mapNotNull { it.toObject(QrDto::class.java)?.toDomain() }
    }
}