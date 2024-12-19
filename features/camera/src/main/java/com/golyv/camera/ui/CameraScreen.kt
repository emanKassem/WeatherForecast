package com.golyv.camera.ui

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.golyv.camera.R
import com.golyv.camera.utils.CameraFileUtils.takePicture
import com.golyv.uicomponents.permission.WithPermission
import com.golyv.uicomponents.screens.error.ErrorScreen
import com.golyv.uicomponents.screens.error.model.Error
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    onImageCaptured: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var permissionDenied by remember {
        mutableStateOf(false)
    }
    val executor = remember { Executors.newSingleThreadExecutor() }
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            bindToLifecycle(lifecycleOwner)
        }
    }

    WithPermission(
        permission = android.Manifest.permission.CAMERA,
        rationaleMessage = stringResource(R.string.camera_permission_required),
        onCanceled = {
            permissionDenied = true
        }
    ) {
        Box(
            modifier = Modifier.navigationBarsPadding()
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_START
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        controller = cameraController
                    }
                },
                onRelease = {
                    cameraController.unbind()
                }
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                onClick = {
                    takePicture(cameraController, context, executor, { uri ->
                        coroutineScope.launch {
                            withContext(Dispatchers.Main) {
                                onImageCaptured(uri.toString())
                            }
                        }
                    }, { exception ->
                        // Error handling logic for image capture failures
                    })
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(R.string.take_picture))
            }

        }
    }
    if (permissionDenied)
        ErrorScreen(error = Error.PERMISSION_DENIED)
}