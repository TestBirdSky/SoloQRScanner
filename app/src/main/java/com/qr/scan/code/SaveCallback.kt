package com.qr.scan.code

import android.content.Context

/**
 * Date：2024/3/4
 * Describe:
 */
interface SaveCallback {
    fun callSuccess(context: Context)
    fun failed(context: Context)
}