package com.qr.scan.code.set

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.qr.scan.code.BaseScanActivity
import com.qr.scan.code.CommonBg
import com.qr.scan.code.CommonTitle
import com.qr.scan.code.IconText
import com.qr.scan.code.R
import com.qr.scan.code.tool.QRTools
import com.qr.scan.code.viewmodel.QrResultViewModel

/**
 * Date：2024/2/26
 * Describe:
 */
class QRCreateResultActivity : BaseScanActivity() {
    private lateinit var permissionW: ActivityResultLauncher<String>
    private val mViewModel: QrResultViewModel by viewModels()
    private var mBitmap: Bitmap? = null
    private var eventType = -1

    @Composable
    override fun SetView() {
        val str = intent.getStringExtra("str") ?: ""
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }

        val isShow = remember { mutableStateOf(false) }
        LaunchedEffect(true) {
            QRTools.buildQRCode(str, this@QRCreateResultActivity, 170, 170) {
                bitmap.value = it
                isShow.value = true
            }
        }
        CommonBg()
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTitle(title = stringResource(R.string.qr_create), back = {
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
                    val si = 180.dp
                    Box(
                        modifier = Modifier
                            .padding(top = 44.dp, bottom = 30.dp)
                            .size(si, si)
                            .border(1.dp, Color(0xffE9E9EA), RoundedCornerShape(16.dp))
                    ) {
                        bitmap.value?.let { bit ->
                            AndroidView(factory = {
                                val image = ImageView(this@QRCreateResultActivity)
                                image.scaleType = ImageView.ScaleType.FIT_XY
                                image.setImageBitmap(bit)
                                image
                            }, modifier = Modifier.fillMaxSize())
                        }
                    }
                    Row {
                        if (isShow.value) {
                            IconText("Download", imageRes = R.drawable.ic_download, onClick = {
                                bitmap.value?.let {
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                                        eventType = 1
                                        permissionW.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    } else {
                                        mViewModel.saveTo(this@QRCreateResultActivity, it)
                                    }
                                }
                            })
                            Spacer(modifier = Modifier.width(38.dp))
                            IconText("Share", imageRes = R.drawable.ic_share, onClick = {
                                bitmap.value?.let {
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                                        eventType = 2
                                        permissionW.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    } else {
                                        share(it)
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    override fun action() {
        val str = intent.getStringExtra("str")
        if (str.isNullOrBlank()) {
            return
        }
        permissionW = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                event()
            }
        }
    }

    private fun event() {
        when (eventType) {
            1 -> {
                mBitmap?.let {
                    mViewModel.saveTo(this@QRCreateResultActivity, it)
                }
            }
            2 -> {
                mBitmap?.let { share(it) }
            }
        }
    }

    private fun share(bitmap: Bitmap) {
        startActivity(
            Intent.createChooser(
                mViewModel.shareIntent(bitmap, this), resources.getString(R.string.app_name)
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mBitmap?.recycle()
    }
}