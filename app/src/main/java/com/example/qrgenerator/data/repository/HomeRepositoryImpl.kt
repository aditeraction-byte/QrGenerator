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
/**
 * Repository handling QR codes for the home screen.
 * Fetches, adds, and deletes QRs for the current user.
 */
class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : HomeRepository {
    /**
     * Returns the Firestore collection reference for the current user's QR codes.
     */
    private fun userQrsCollection() =
        firestore.collection("users")
            .document(auth.currentUser?.uid ?: throw Exception("User not logged in"))
            .collection("home_qrs")
    /**
     * Reference to the public QR codes collection in Firestore.
     */
    private val publicQrsCollection = firestore.collection("public_qrs")
    /**
     * Retrieves all QR codes owned by the current user.
     */
    override suspend fun getAllQrs(): List<QrDomain> {
        val userQrsSnap = userQrsCollection().get().await()
        val qrIds = userQrsSnap.documents.mapNotNull { it.getString("qrId") }

        val qrList = mutableListOf<QrDomain>()
        for (id in qrIds) {
            val qrSnap = publicQrsCollection.document(id).get().await()
            qrSnap.toObject(QrDto::class.java)?.toDomain()?.let { qrList.add(it) }
        }
        return qrList
    }
    /**
     * Adds a new QR code for the current user.
     * Throws exception if a QR with the same ID already exists.
     */
    override suspend fun addQr(qr: QrDomain) {
        val currentUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val qrWithOwner = qr.copy(ownerUid = currentUid)


        val docRef = publicQrsCollection.document(qr.id)
        if (docRef.get().await().exists()) {
            throw Exception("A QR with this ID already exists")
        }

        docRef.set(qrWithOwner.toDto()).await()

        userQrsCollection().document(qr.id).set(mapOf("qrId" to qr.id)).await()
    }
    /**
     * Deletes a QR code owned by the current user, including all its scans.
     * Throws exception if the QR does not exist or is not owned by the user.
     */
    override suspend fun deleteQr(qrId: String) {
        val currentUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val qrSnap = publicQrsCollection.document(qrId).get().await()
        val qr = qrSnap.toObject(QrDto::class.java)?.toDomain()
            ?: throw Exception("QR not found")

        if (qr.ownerUid != currentUid) {
            throw Exception("Cannot delete a QR you do not own")
        }

        // Delete all scans of this QR
        val scansCollection = publicQrsCollection.document(qrId).collection("scans").get().await()
        scansCollection.documents.forEach { it.reference.delete().await() }

        // Delete QR document and user's reference
        publicQrsCollection.document(qrId).delete().await()
        userQrsCollection().document(qrId).delete().await()
    }
}