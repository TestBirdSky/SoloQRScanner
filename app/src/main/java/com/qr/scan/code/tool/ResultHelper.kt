package com.qr.scan.code.tool

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.qr.scan.code.R
import com.qr.scan.code.SaveCallback
import java.io.ByteArrayOutputStream

/**
 * Date：2024/3/4
 * Describe:
 */
class ResultHelper : SaveCallback {
    private var isSaveSuccess = false
    fun getShareBitmapToOtherAppIntent(context: Context, bitmap: Bitmap): Intent {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.applicationContext.contentResolver,
            bitmap,
            context.resources.getString(R.string.app_name),
            "Code"
        )
        val uri = Uri.parse(path)
        return Intent().apply {
            type = "image/*"
            action = Intent.ACTION_SEND
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_STREAM, uri)
        }
    }

    fun saveToMediaStore(
        context: Context, bitmap: Bitmap, flag: Long = System.currentTimeMillis()
    ) {
        if (isSaveSuccess) {
            this.callSuccess(context)
            return
        }
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 99, bytes)
        runCatching {
            val path: String = MediaStore.Images.Media.insertImage(
                context.applicationContext.contentResolver,
                bitmap,
                context.resources.getString(R.string.app_name),
                "Image_$flag"
            )
            MediaScannerConnection.scanFile(
                context.applicationContext, arrayOf(path), null
            ) { _, uri ->
                // 扫描完成后的操作
                this.callSuccess(context)
            }
        }.onFailure {
            this.failed(context)
        }
    }

    override fun callSuccess(context: Context) {
        Toast.makeText(context, "Save Success", Toast.LENGTH_LONG).show()
    }

    override fun failed(context: Context) {
        Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show()
    }
}