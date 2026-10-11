package edu.sdgku.stepcounter.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun StepCounterTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}