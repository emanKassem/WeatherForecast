package com.golyv.uicomponents.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.PermissionChecker
import com.golyv.uicomponents.R
import com.golyv.uicomponents.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun WithPermission(
    permission: String,
    rationaleMessage: String = stringResource(R.string.permissions_rationale_title),
    onCanceled: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    var permissionDenied by remember { mutableStateOf(false) }
    if (PermissionChecker.checkSelfPermission(
            context,
            permission
        ) == PermissionChecker.PERMISSION_GRANTED
    )
        content()
    else {
        if (!permissionDenied) {
            val permissionState = rememberPermissionState(permission = permission)
            PermissionRequired(
                permissionState = permissionState,
                permissionNotGrantedContent = {
                    TitleSubtitleActionsDialog(
                        title = rationaleMessage,
                        actionOne = stringResource(R.string.confirm),
                        onActionOne = { permissionState.launchPermissionRequest() },
                        actionTwo = stringResource(R.string.cancel),
                        onActionTwo = {
                            permissionDenied = true
                            onCanceled()
                        }
                    )
                },
                permissionNotAvailableContent = {
                    TitleSubtitleActionsDialog(
                        title = stringResource(R.string.permissions_rationale_title),
                        actionOne = stringResource(R.string.open_settings),
                        onActionOne = {
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.parse("package:" + context.packageName)
                                context.startActivity(this)
                            }
                        },
                        actionTwo = stringResource(R.string.cancel),
                        onActionTwo = {
                            permissionDenied = true
                            onCanceled()
                        }
                    )
                },
                content = content
            )
        }

    }
}

@Composable
private fun TitleSubtitleActionsDialog(
    title: String? = null,
    subtitle: String? = null,
    actionOne: String? = null,
    actionTwo: String? = null,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    ),
    onActionOne: () -> Unit = {},
    onActionTwo: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {},
        properties = dialogProperties
    ) {
        Card(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        ) {
            TitleSubtitleAndTwoActionButtons(
                title = title,
                subtitle = subtitle,
                actionOne = actionOne,
                onActionOne = onActionOne,
                actionTwo = actionTwo,
                onActionTwo = onActionTwo
            )
        }
    }
}

@Composable
private fun TitleSubtitleAndTwoActionButtons(
    icon: Int? = R.drawable.ic_warning,
    title: String? = null,
    subtitle: String? = null,
    actionOne: String? = null,
    onActionOne: () -> Unit = {},
    actionTwo: String? = null,
    onActionTwo: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon != null) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(icon),
                contentDescription = "imageHeader",
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        if (title != null)
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 5.dp),
                letterSpacing = 2.sp,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = subtitle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 5.dp),
                letterSpacing = 2.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (actionOne != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onActionOne,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                content = {
                    Text(
                        text = actionOne,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        }
        if (actionTwo != null) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onActionTwo,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.surface),
                content = {
                    Text(
                        actionTwo,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun PreviewTitleSubtitleAndTwoActionButtons() {
    TitleSubtitleAndTwoActionButtons {}
}

@Composable
@Preview
private fun PreviewDialog() {
    AppTheme {
        Card(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        ) {
            TitleSubtitleAndTwoActionButtons(
                title = "title",
                subtitle = "subtitle", actionOne = "actionOne",
                onActionOne = {},
                actionTwo = "actionTwo",
                onActionTwo = {}
            )
        }
    }
}