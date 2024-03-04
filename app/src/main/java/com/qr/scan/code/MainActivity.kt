package com.qr.scan.code

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.qr.scan.code.set.QrCreateActivity
import com.qr.scan.code.set.QrSettingActivity
import com.qr.scan.code.tool.QRTools
import com.qr.scan.code.ui.theme.QRScanTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseScanActivity() {
    private val mScanner by lazy { GmsBarcodeScanning.getClient(this) }

    @Composable
    override fun SetView() {
        Image(
            painter = painterResource(R.drawable.ic_home_bg),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 33.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp),
                )
                Box(modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer {
                        clip = true
                        shape = CircleShape
                    }
                    .background(Color(0x89FFFFFF))) {
                    Image(painter = painterResource(R.drawable.ic_setting),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                startActivity(
                                    Intent(
                                        this@MainActivity, QrSettingActivity::class.java
                                    )
                                )
                            }
                            .padding(6.dp))
                }
            }
            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    ButtonHomeS(painterResource(R.drawable.ic_home_scanner),
                        width = 73.dp,
                        painterResource(R.drawable.ic_scanner_btn_bg),
                        click = {
                            mScanner.startScan().addOnSuccessListener {
                                QRTools.bitToIntent(it)?.apply {
                                    setClass(this@MainActivity, ScanResultActivity::class.java)
                                    startActivity(this)
                                }
                            }.addOnFailureListener {
                                lifecycleScope.launch(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@MainActivity, "${it.message}", Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        })
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    ButtonHomeS(painterResource(R.drawable.ic_home_creat_qr),
                        width = 79.dp,
                        painterResource(R.drawable.ic_home_create_qr),
                        click = {
                            startActivity(Intent(this@MainActivity, QrCreateActivity::class.java))
                        })
                }
            }
        }
    }

    override fun action() {

    }

    @Composable
    fun ButtonHomeS(text: Painter, width: Dp, bg: Painter, click: () -> Unit) {
        Box(
            modifier = Modifier.background(Color.Transparent)
        ) {
            Image(painter = bg,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(149.5.dp)
                    .clickable(role = Role.Image) {
                        click.invoke()
                    })
            Image(
                painter = text,
                contentDescription = "",
                modifier = Modifier
                    .padding(16.dp)
                    .height(32.dp)
                    .width(width)
            )
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QRScanTheme {
        Greeting("Android")
    }
}