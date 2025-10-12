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
/**
 * Repository for creating, fetching, and updating QR codes globally.
 * Handles permission checks to ensure the current user owns the QR before updates.
 */
class QrGeneratorRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : QrGeneratorRepository {
    /**
     * Returns Firestore path for the current user's QR references.
     */
    private fun userQrsPath() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?: throw Exception("User not logged in"))
            .collection("home_qrs")
    /**
     * Reference to the global public QR codes collection.
     */
    private val publicQrsPath = firestore.collection("public_qrs")
    /**
     * Creates a new QR code in public collection and adds a reference to the current user.
     */
    override suspend fun createQr(qr: QrDomain) {
        val qrWithOwner = qr.copy(ownerUid = auth.currentUser?.uid ?: "")
        val docRef = publicQrsPath.document(qr.id)
        val snapshot = docRef.get().await()

        if (snapshot.exists()) {
            throw Exception("A QR with this Shortlink already exists")
        }

        docRef.set(qrWithOwner.toDto()).await()

        userQrsPath().document(qr.id).set(mapOf("qrId" to qr.id)).await()
    }
    /**
     * Fetches a QR by its ID.
     * Throws exception if the QR does not exist.
     */
    override suspend fun getQrById(id: String): QrDomain {
        val doc = publicQrsPath.document(id).get().await()
        return doc.toObject(QrDto::class.java)?.toDomain() ?: throw Exception("QR not found")
    }
    /**
     * Fetches all public QR codes.
     */
    override suspend fun getAllQrs(): List<QrDomain> {
        val snap = publicQrsPath.get().await()
        return snap.documents.mapNotNull { it.toObject(QrDto::class.java)?.toDomain() }
    }
    /**
     * Updates a QR code if owned by the current user.
     * Throws exception if the QR does not exist or is not owned by the user.
     */
    override suspend fun updateQr(qr: QrDomain) {
        val docRef = publicQrsPath.document(qr.id)
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) throw Exception("QR not found")

        val existing = snapshot.toObject(QrDto::class.java)?.toDomain()
        if (existing?.ownerUid != auth.currentUser?.uid) throw Exception("Cannot edit a QR you do not own")

        docRef.set(qr.toDto()).await()
    }
}