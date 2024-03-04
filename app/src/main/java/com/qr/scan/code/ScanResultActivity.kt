package com.qr.scan.code

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qr.scan.code.viewmodel.ScanResultViewModel
import kotlinx.coroutines.delay

/**
 * Date：2024/2/26
 * Describe:
 */
class ScanResultActivity : BaseScanActivity() {

    private val mViewModel by viewModels<ScanResultViewModel>()

    @Composable
    override fun SetView() {
        val isShow = remember {
            mutableStateOf(false)
        }
        val str = intent.getStringExtra("result") ?: ""
        val type = intent.getStringExtra("t") ?: "Text"
        CommonBg()
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTitle(title = stringResource(R.string.scan_result), back = {
                finish()
            })
            Box(modifier = Modifier
                .padding(top = 44.dp, bottom = 29.5.dp)
                .fillMaxSize()
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                }
                .background(Color.White)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .height(174.dp)
                            .border(1.dp, Color(0xffE9E9EA), RoundedCornerShape(16.dp))
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp, 12.dp)
                                        .background(Color(0xff5961FF))
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = type,
                                    modifier = Modifier,
                                    color = Color(0xFF23262F),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = str,
                                fontSize = 14.sp,
                                color = Color(0xFF23262F),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Row {
                        IconText("Copy", imageRes = R.drawable.ic_download, onClick = {
                            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText(null, str)
                            clipboard.setPrimaryClip(clip)
                            isShow.value = true
                        })
                        Spacer(modifier = Modifier.width(width = 38.5.dp))
                        IconText("Share", imageRes = R.drawable.ic_share, onClick = {
                            mViewModel.shareText(str, this@ScanResultActivity)
                        })
                        Spacer(modifier = Modifier.width(width = 38.5.dp))
                        IconText("Search", imageRes = R.drawable.ic_search_url, onClick = {
                            when (type) {
                                "URL" -> {
                                    val ina = Intent(Intent.ACTION_VIEW)
                                    ina.data = Uri.parse(str)
                                    startActivity(ina)
                                }

                                else -> {
                                    val ina = Intent(Intent.ACTION_VIEW)
                                    ina.data = Uri.parse(mViewModel.getSearchUrl(str))
                                    startActivity(ina)
                                }
                            }
                        })
                    }
                }
            }

        }
        if (isShow.value) {
            LaunchedEffect(key1 = "", block = {
                delay(800)
                isShow.value = false
            })
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80A7A8AC))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp, 140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_success),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Copy Successful!",
                                fontSize = 14.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF23262F)
                            )
                        }
                    }
                }
            }
        }

    }

    override fun action() {

    }
}