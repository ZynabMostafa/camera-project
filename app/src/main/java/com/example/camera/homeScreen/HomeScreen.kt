package com.example.camera.homeScreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.camera.R
import com.example.camera.database.BuilderClass
import com.example.camera.database.UserImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(activity: Activity, builderClass: BuilderClass) {
    var allowOrNot by remember {
        mutableStateOf(false)
    }
    var list by remember {
        mutableStateOf<List<Bitmap?>>(builderClass.initDao().getAllImage())
    }
    var bitmab by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val pickPhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        bitmab = it
    }
    val cameraPermition =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    pickPhoto.launch()
                    return@rememberLauncherForActivityResult
                }
                allowOrNot = true

            })

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(onClick = {
                cameraPermition.launch(Manifest.permission.CAMERA)
                builderClass.run {
                    initDao().insetImage(UserImage(image = bitmab))
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_camera),
                    contentDescription = ""
                )
            }
        }
    ) {

        Box(modifier = Modifier.padding(it)) {

            PoP(visable = allowOrNot, activity = activity) {
                allowOrNot = false
            }

            LazyColumn(
                state = rememberLazyListState(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
            ) {
                list = builderClass.initDao().getAllImage()
                items(list) { bit ->

                    bit?.let {
                        Image(bitmap = bit.asImageBitmap(), contentDescription = "")
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }

        }
    }
}

@Composable
fun PoP(
    visable: Boolean,
    activity: Activity,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(visible = visable) {
        AlertDialog(
            title = {
                Text(text = "Confirm Go To Setting")
            },
            text = {
                Text(text = "Are you sure you want to give permission")
            },
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                Button(onClick = {
                    openAppSettings(activity = activity)

                }) {
                    Text(text = "Yes")
                }

            },
            dismissButton = {
                OutlinedButton(onClick = { onDismiss() }) {
                    Text(text = "No")
                }
            }
        )
    }

}

fun openAppSettings(activity: Activity) {
    val intent = Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", activity.packageName, null)
    )
    activity.startActivity(intent)

}

