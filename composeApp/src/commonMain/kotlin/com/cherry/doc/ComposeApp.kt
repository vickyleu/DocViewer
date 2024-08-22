package com.cherry.doc

import androidx.compose.runtime.Composable
import com.cherry.lib.doc.DocumentScreen

@Composable
fun ComposeApp(){
    PermissionGrant{
        DocumentScreen()
    }
}

@Composable
expect fun PermissionGrant(content: @Composable () -> Unit = {})