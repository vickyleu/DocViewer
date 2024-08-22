package com.cherry.doc

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

private const val TAG = "PermissionGrant"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun PermissionGrant(content: @Composable () -> Unit) {
    val readExtPermission =
        rememberMultiplePermissionsState(
            mutableListOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // A13
                    add(Manifest.permission.READ_MEDIA_AUDIO)
                    add(Manifest.permission.READ_MEDIA_IMAGES)
                    add(Manifest.permission.READ_MEDIA_VIDEO)
                }
            }
        )
    val context = LocalPlatformContext.current
    LaunchedEffect(key1 = Unit) {
        println("PermissionGrant readExtPermission.allPermissionsGranted:${readExtPermission.allPermissionsGranted}")
        if (!readExtPermission.allPermissionsGranted)
            readExtPermission.launchMultiplePermissionRequest()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {// A11
            if (!Environment.isExternalStorageManager()) {
                kotlin.runCatching {
                    context.startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = Uri.parse("package:${context.packageName}")
                    })
                }.onFailure {
                    Log.e(TAG, "ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", it)
                }
            }
        }
    }

    if (!readExtPermission.allPermissionsGranted) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        } else {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                if (readExtPermission.allPermissionsGranted) {
                    content.invoke()
                } else {
                    Text(
                        text = "无权限",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .border(1.dp, Color.Blue, RoundedCornerShape(5.dp))
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                            .clickable {
                                readExtPermission.launchMultiplePermissionRequest()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {// A11
                                    if (!Environment.isExternalStorageManager()) {
                                        kotlin
                                            .runCatching {
                                                context.startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                                                    data =
                                                        Uri.parse("package:${context.packageName}")
                                                })
                                            }
                                            .onFailure {
                                                Log.e(
                                                    TAG,
                                                    "ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION",
                                                    it
                                                )
                                            }
                                    }
                                }
                            },
                        color = Color.Black
                    )
                }
            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {// A11
        if (!Environment.isExternalStorageManager()) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                if (readExtPermission.allPermissionsGranted) {
                    content.invoke()
                } else {
                    Text(
                        text = "无权限",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .border(1.dp, Color.Blue, RoundedCornerShape(5.dp))
                            .padding(horizontal = 16.dp, vertical = 5.dp)
                            .clickable {
                                readExtPermission.launchMultiplePermissionRequest()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {// A11
                                    if (!Environment.isExternalStorageManager()) {
                                        kotlin
                                            .runCatching {
                                                context.startActivity(Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                                                    data =
                                                        Uri.parse("package:${context.packageName}")
                                                })
                                            }
                                            .onFailure {
                                                Log.e(
                                                    TAG,
                                                    "ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION",
                                                    it
                                                )
                                            }
                                    }
                                }
                            },
                        color = Color.Black
                    )
                }
            }
        }
    }
}