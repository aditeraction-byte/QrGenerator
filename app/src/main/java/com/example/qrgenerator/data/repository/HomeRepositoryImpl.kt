package com.example.qrgenerator.data.repository


import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.mapper.toDto
import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.domain.repository.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HomeRepository {

    private fun userQrsCollection() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?: throw Exception("User not logged in"))
            .collection("home_qrs")

    override suspend fun getAllQrs(): List<QrDomain> {
        val snap = userQrsCollection().get().await()
        return snap.documents.mapNotNull { it.toObject(QrDto::class.java)?.toDomain() }
    }

    override suspend fun addQr(qr: QrDomain) {
        userQrsCollection().document(qr.id).set(qr.toDto()).await()
    }

    override suspend fun deleteQr(qrId: String) {
        userQrsCollection().document(qrId).delete().await()
    }
}