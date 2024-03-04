package com.qr.scan.code

import android.app.Application
import com.qr.solo.OkHttpManager

/**
 * Date：2024/3/4
 * Describe:
 */
class SoloApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OkHttpManager.requestC(this, getSharedPreferences("Solo QR", MODE_PRIVATE))
    }
}