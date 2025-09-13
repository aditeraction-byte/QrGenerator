package com.example.qrgenerator.utils.helpers


import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.core.graphics.set
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt

object QrHelper {
    fun generateQrBitmap(
        content: String,
        fgColorHex: String = "#000000",
        bgColorHex: String = "#FFFFFF",
        size: Int = 512
    ): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            size,
            size
        )
        val bitmap = createBitmap(size, size, Bitmap.Config.RGB_565)
        val fgColor = fgColorHex.toColorInt()
        val bgColor = bgColorHex.toColorInt()
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap[x, y] = if (bitMatrix[x, y]) fgColor else bgColor
            }
        }
        return bitmap
    }
}

