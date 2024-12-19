package com.golyv.uicomponents.snackbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun SnackBar(
    modifier: Modifier = Modifier,
    message: String,
    showSb: Boolean,
    openSnackBar: (Boolean) -> Unit
) {

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(
        modifier = modifier,
        hostState = snackState
    ){
        Snackbar(
            snackbarData = it,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface
        )
    }


    if (showSb){
        LaunchedEffect(Unit) {
            snackScope.launch { snackState.showSnackbar(message) }
            openSnackBar(false)
        }

    }


}