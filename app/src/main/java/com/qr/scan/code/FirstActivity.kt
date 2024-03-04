package com.qr.scan.code

import android.content.Intent
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Date：2024/2/26
 * Describe:
 */
class FirstActivity : BaseScanActivity() {

    @Composable
    override fun SetView() {
        Image(
            painter = painterResource(id = R.drawable.ic_sp_bg),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(bottom = 60.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sp_code),
                contentDescription = "",
                modifier = Modifier.size(90.dp)
            )
            Text(
                text = getString(R.string.app_name),
                modifier = Modifier.padding(top = 10.dp, bottom = 40.dp),
                color = Color.White,
                fontSize = 26.sp
            )

            RotationView()
        }
    }

    override fun action() {
        ModuleInstall.getClient(this).installModules(
            ModuleInstallRequest.newBuilder()
            .addApi(GmsBarcodeScanning.getClient(this)).build())
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@FirstActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    @Composable
    fun RotationView() {
        val zRotationAnimation2 = remember { Animatable(0f) }
        LaunchedEffect(true) {
            zRotationAnimation2.animateTo(
                360f, animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 600)
                )
            )
        }
        Image(painter = painterResource(id = R.drawable.ic_loading),
            contentDescription = "",
            modifier = Modifier
                .size(48.dp)
                .graphicsLayer {
                    rotationZ = zRotationAnimation2.value
                })
    }
}