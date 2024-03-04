package com.qr.solo

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Date：2024/3/4
 * Describe:
 */
object OkHttpManager {
    private val C_URL = "https://jive.solodesignerqr.com/peal/audubon"
    private val okHttpClient = OkHttpClient()
    private var infoHelper: InfoHelper? = null
    private lateinit var mSp: SharedPreferences
    private val KEYC = "Cloak_Status"

    fun requestC(app: Application, sp: SharedPreferences) {
        mSp = sp
        if (getCCache().isNotBlank()) return
        infoHelper = InfoHelper(app)
        val request = Request.Builder().get().url(infoHelper!!.createUrl(C_URL)).build()
        cirR(request)
    }

    private fun cirR(request: Request) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "onResponse:-->$e")
            }

            override fun onResponse(call: Call, response: Response) {
                val bo = response.body?.string() ?: ""
                Log.i("TAG", "onResponse:-->$bo ")
                if (response.isSuccessful && response.code == 200) {
                    saveCC(bo)
                }
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            delay(10000)
            if (getCCache().isBlank()) {
                cirR(request)
            }
        }
    }


    private fun getCCache(): String {
        return mSp.getString(KEYC, "") ?: ""
    }

    private fun saveCC(string: String) {
        mSp.edit().putString(KEYC, string).apply()
    }
}