package com.qr.scan.code.tool

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.toPixelMap
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.qr.scan.code.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Hashtable

/**
 * Date：2024/3/1
 * Describe:
 */
object QRTools {

    suspend fun buildQRCode(
        data: String, context: Context, width: Int, height: Int, success: (bit: Bitmap) -> Unit = {}
    ) {
        withContext(Dispatchers.IO) {
            val writer = QRCodeWriter()
            val hints = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints)
//            val bmp = ImageBitmap(width, height, ImageBitmapConfig.Argb8888)
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val white = context.resources.getColor(R.color.white, null)
            for (x in 0 until width) {
                for (y in 0 until height) {
//                    bmp.toPixelMap(x, y, if (bitMatrix.get(x, y)) Color.BLACK else white)
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else white)
                }
            }
            withContext(Dispatchers.Main) {
                success.invoke(bmp)
            }
        }
    }

    fun bitToIntent(barcode: Barcode): Intent? {
        val type = if (barcode.valueType == Barcode.TYPE_URL) "URL" else "Text"
        val result = barcode.rawValue
        if (result != null) {
            return Intent().apply {
                putExtra("t", type)
                putExtra("result", result)
            }
        }
        return null
    }

}