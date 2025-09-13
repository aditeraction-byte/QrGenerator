package com.example.qrgenerator.data.repository

import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.mapper.toDto
import com.example.qrgenerator.data.model.QrScanDto
import com.example.qrgenerator.domain.model.QrScanDomain
import com.example.qrgenerator.domain.repository.QrScanRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QrScanRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
) : QrScanRepository {

    override suspend fun getScansForQr(qrId: String): List<QrScanDomain> {
        val snap = firestore.collection("public_qrs")
            .document(qrId)
            .collection("scans")
            .get()
            .await()

        return snap.documents.mapNotNull { it.toObject(QrScanDto::class.java)?.toDomain() }
    }
    override suspend fun addScan(scan: QrScanDomain) {
        firestore.collection("public_qrs")
            .document(scan.qrId)
            .collection("scans")
            .add(scan.toDto())
            .await()
    }
}