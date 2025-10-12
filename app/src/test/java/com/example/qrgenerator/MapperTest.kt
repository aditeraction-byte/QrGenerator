package com.example.qrgenerator

import com.example.qrgenerator.data.mapper.toDomain
import com.example.qrgenerator.data.mapper.toDto
import com.example.qrgenerator.data.model.QrDto
import com.example.qrgenerator.data.model.QrScanDto
import com.example.qrgenerator.data.model.UserDto
import org.junit.Assert.*
import org.junit.Test

class MapperTest {

    @Test
    fun `QrDto toDomain and back`() {
        val dto = QrDto(id = "123", redirectUrl = "url", title = "title")
        val domain = dto.toDomain()
        val dtoBack = domain.toDto()

        assertEquals(dto.id, dtoBack.id)
        assertEquals(dto.redirectUrl, dtoBack.redirectUrl)
        assertEquals(dto.title, dtoBack.title)
    }
    @Test
    fun `UserDto toDomain and back`() {
        val dto = UserDto(uid = "uid", email = "email@test.com")
        val domain = dto.toDomain()
        val dtoBack = domain.toDto()

        assertEquals(dto.uid, dtoBack.uid)
        assertEquals(dto.email, dtoBack.email)
    }
    @Test
    fun `QrScanDto toDomain and back`() {
        val dto = QrScanDto(qrId = "qr1", timestamp = com.google.firebase.Timestamp(1000,0))
        val domain = dto.toDomain()
        val dtoBack = domain.toDto()

        assertEquals(dto.qrId, dtoBack.qrId)
        // timestamp in seconds → domain is in millis
        assertEquals(dto.timestamp?.seconds ?: 0L, dtoBack.timestamp?.seconds ?: 0L)
    }

}