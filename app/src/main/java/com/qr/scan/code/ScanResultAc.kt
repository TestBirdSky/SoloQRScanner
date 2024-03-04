package com.qr.scan.code

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

/**
 * Date：2024/2/26
 * Describe:
 */
class ScanResultAc : BaseScanActivity() {

    @Composable
    override fun SetView() {
        CommonBg()
        CommonTitle(title = stringResource(R.string.scan_title))
    }

    override fun action() {

    }
}