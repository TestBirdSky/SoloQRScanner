package com.qr.scan.code.set

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.qr.scan.code.BaseScanActivity
import com.qr.scan.code.CommonBg
import com.qr.scan.code.CommonTitle
import com.qr.scan.code.R

/**
 * Date：2024/2/26
 * Describe:
 */
class QrWebViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CommonBg()
            Column(modifier = Modifier.fillMaxSize()) {
                CommonTitle(title = stringResource(R.string.privacy_agreement), back = {
                    finish()
                })
                AndroidView(factory = { context ->
                    WebView(context)
                }) {
                    it.settings.javaScriptEnabled = true
                    it.loadUrl(getString(R.string.privacy_url))
                }
            }
        }

    }

}