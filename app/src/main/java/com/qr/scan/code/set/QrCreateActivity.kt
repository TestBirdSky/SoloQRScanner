package com.qr.scan.code.set

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qr.scan.code.BaseScanActivity
import com.qr.scan.code.CommonBg
import com.qr.scan.code.CommonTitle
import com.qr.scan.code.R

/**
 * Date：2024/2/26
 * Describe:
 */
class QrCreateActivity : BaseScanActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun SetView() {
        var textS by remember { mutableStateOf("") }
        CommonBg()
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTitle(title = stringResource(R.string.qr_create), back = {
                finish()
            })
            Box(modifier = Modifier
                .padding(top = 44.dp)
                .fillMaxSize()
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                }
                .background(Color.White)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .height(174.dp)
                            .border(0.5.dp, Color(0xffE9E9EA), RoundedCornerShape(16.dp))
                    ) {
                        TextField(
                            value = textS,
                            textStyle = TextStyle(
                                color = Color(0xFF23262F),
                                textAlign = TextAlign.Left,
                                background = Color.White,
                            ),
                            modifier = Modifier.fillMaxSize(),
                            onValueChange = {
                                textS = it
                            },
                            label = { Text("") },
                            placeholder = {
                                Text(
                                    text = "Please enter text…",
                                    fontSize = 14.sp,
                                    color = Color(0xff4F5159)
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(16.dp),
                        )
                    }

                    Button(onClick = {
                        if (textS.isNotBlank()) {
                            startActivity(Intent(
                                this@QrCreateActivity, QRCreateResultActivity::class.java
                            ).apply {
                                putExtra("str", textS)
                            })
                        }
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffF4F4F4),
                            contentColor = Color(0xffF4F4F4),
                        ),
                        modifier = Modifier
                            .padding(top = 33.dp)
                            .size(148.dp, 49.dp)
                            .graphicsLayer {
                                clip = true
                                shape = RoundedCornerShape(30.dp)
                            }
                            .background(Color(0xffF4F4F4))) {
                        Text(
                            text = stringResource(R.string.create),
                            color = Color(0xFF23262F),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

    override fun action() {

    }
}