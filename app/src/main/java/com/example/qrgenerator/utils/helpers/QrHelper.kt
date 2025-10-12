package com.example.qrgenerator.utils.helpers


import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import androidx.core.graphics.set
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt

// Utility object responsible for generating QR code bitmaps.
object QrHelper {

    /**
     * Generates a [Bitmap] representing a QR code for the given content.
     *
     * @param content The text or URL to encode inside the QR.
     * @param fgColorHex Foreground color in HEX format (default: black).
     * @param bgColorHex Background color in HEX format (default: white).
     * @param size The width/height of the resulting bitmap in pixels (default: 512).
     * @return A [Bitmap] representing the generated QR code.
     *
     * @throws IllegalArgumentException If content is blank or size is not positive.
     */

    fun generateQrBitmap(
        content: String,
        fgColorHex: String = "#000000",
        bgColorHex: String = "#FFFFFF",
        size: Int = 512
    ): Bitmap {
        // Validate parameters early to prevent crashes.
        require(size > 0) { "QR size must be greater than 0" }
        require(content.isNotBlank()) { "Content cannot be blank" }

        // Encode the input text into a QR matrix.
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            size,
            size
        )

        // Create a mutable bitmap and define QR / background colors.
        val bitmap = createBitmap(size, size, Bitmap.Config.RGB_565)
        val fgColor = fgColorHex.toColorInt()
        val bgColor = bgColorHex.toColorInt()

        // Iterate over each pixel and set its color based on the BitMatrix value.
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap[x, y] = if (bitMatrix[x, y]) fgColor else bgColor
            }
        }

        return bitmap
    }
}

