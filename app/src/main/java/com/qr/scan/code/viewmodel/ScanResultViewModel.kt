package com.qr.scan.code.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel

/**
 * Date：2024/3/4
 * Describe:
 */
class ScanResultViewModel : ViewModel() {
    private val Google_URL = "https://www.google.com/search?q="

    fun getSearchUrl(searchContext: String): String {
        return "$Google_URL$searchContext"
    }

    fun shareText(string: String, context: Context) {
        context.apply {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT, string
            )
            startActivity(Intent.createChooser(intent, "Share"))
        }
    }

}