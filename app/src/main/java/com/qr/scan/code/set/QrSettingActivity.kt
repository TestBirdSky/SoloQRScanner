package com.qr.scan.code.set

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qr.scan.code.BaseScanActivity
import com.qr.scan.code.CommonTitle
import com.qr.scan.code.R

/**
 * Date：2024/2/26
 * Describe:
 */
class QrSettingActivity : BaseScanActivity() {
    @Composable
    override fun SetView() {
        Image(
            painter = painterResource(R.drawable.ic_setting_bg),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxSize()) {
            CommonTitle(
                title = stringResource(R.string.str_setting),
                color = Color(0xff23262F),
                back = {
                    finish()
                })
            Box(modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxSize()
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                }
                .background(Color.White)
            ) {
                Row(modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable {
                        startActivity(
                            Intent(
                                this@QrSettingActivity, QrWebViewActivity::class.java
                            )
                        )
                    }
                    .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_privacy),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = stringResource(R.string.privacy_agreement),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp),
                        fontSize = 16.sp,
                        color = Color(0xff23262F)
                    )
                    Image(
                        painter = painterResource(R.mipmap.ic_next_s),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
        }
    }

    override fun action() {

    }
}