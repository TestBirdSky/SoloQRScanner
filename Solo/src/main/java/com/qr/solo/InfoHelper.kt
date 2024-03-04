package com.qr.solo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.UUID

/**
 * Date：2024/3/4
 * Describe:
 */
class InfoHelper(val context: Application) {
    private var pacifism = ""
    private var mAndroidId = ""

    @SuppressLint("HardwareIds")
    fun getAndo(): String {
        if (mAndroidId.isBlank()) {
            mAndroidId = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
            ).ifBlank { UUID.randomUUID().toString() }
        }
        return mAndroidId
    }

    //mercury
    fun createUrl(baseUrl: String): String {
        return "$baseUrl?faucet=${androidToDis(getAndo())}&esmark=${System.currentTimeMillis()}&languid=${Build.MODEL}" +
                "&symptom=${getP()}&ftc=${Build.VERSION.RELEASE}&pacifism=$pacifism&bahama=${getAndo()}" +
                "&attempt=mercury&eddie=${getPackInfoVersion()}&haggard=${Build.BRAND}"
    }

    private fun getP():String{
//        return "com.solo.designer.qr.scanner"
        return context.packageName
    }

    private fun getPackInfoVersion(): String {
        return context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }
}