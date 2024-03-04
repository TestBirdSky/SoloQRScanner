package com.qr.scan.code

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qr.scan.code.ui.theme.QRScanTheme

/**
 * Date：2024/2/26
 * Describe:
 */
abstract class BaseScanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SetView()
                }
            }
        }
        action()
    }

    @Composable
    abstract fun SetView()

    abstract fun action()
}

@Composable
fun CircleBackground(content: @Composable () -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val radius = with(LocalDensity.current) { maxWidth / 2 } // 计算半径
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Blue,
                center = Offset(radius.value, radius.value),
                radius = radius.value
            )
        }
        content()
    }
}

@Composable
fun CommonBg() {
    Image(
        painter = painterResource(R.drawable.ic_common_bg),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun CommonTitle(title: String, color: Color = Color(0xFFFFFFFF), back: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    back.invoke()
                })
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = color
        )
    }
}


@Composable
fun IconText(text: String, imageRes: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 29.dp, bottom = 8.5.dp)
                .size(63.dp, 63.dp)
                .clickable {
                    onClick.invoke()
                })
        Text(
            text = text, fontSize = 14.sp, color = Color(0xff23262F)
        )
    }
}