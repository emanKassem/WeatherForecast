package com.golyv.uicomponents.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.golyv.uicomponents.theme.AppTheme

/**
 * A composable function that displays a loading overlay with a progress indicator and a description text.
 *
 * @param backgroundColor The background color of the overlay.
 */
@Composable
fun LoadingScreen(
    backgroundColor: Color = colorScheme.background.copy(alpha = 0.6f),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .size(96.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                DotsLoading(
                    dotSize = 8.dp
                )
            }

        }
    }
}

@Preview(name = "en", locale = "en", backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun PreviewProgressBarOverlay() {
    AppTheme {
        LoadingScreen(
            backgroundColor = colorScheme.background,
        )
    }
}