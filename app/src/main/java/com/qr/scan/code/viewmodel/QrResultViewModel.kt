package com.qr.scan.code.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.qr.scan.code.tool.ResultHelper

/**
 * Date：2024/3/4
 * Describe:
 */
class QrResultViewModel : ViewModel() {
    private val resultHelper by lazy { ResultHelper() }

    fun shareIntent(bitmap: Bitmap, context: Context): Intent {
        return resultHelper.getShareBitmapToOtherAppIntent(context, bitmap)
    }

    fun saveTo(context: Context, bitmap: Bitmap) {
        resultHelper.saveToMediaStore(context, bitmap)
    }

}