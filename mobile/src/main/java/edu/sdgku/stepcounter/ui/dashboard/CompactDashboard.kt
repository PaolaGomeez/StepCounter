package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CompactDashboard(
    stepsGoal: Int,
    sendStatus: String,
    layoutLabel: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onSendToWatch: () -> Unit,
    onSaveToFirebase: () -> Unit,
    onGoalDropped: ((Int) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // phone in landscape: content can scroll
    ) {
        Text(
            text = "Layout: $layoutLabel",
            modifier = Modifier.padding(
                start = DashboardDimens.compactStartPadding,
                top = DashboardDimens.compactTopPadding
            ),
            style = MaterialTheme.typography.labelLarge
        )

        GoalEditorPane(
            stepsGoal = stepsGoal,
            sendStatus = sendStatus,
            showStatusHere = true,
            onDecrease = onDecrease,
            onIncrease = onIncrease,
            onSendToWatch = onSendToWatch,
            onSaveToFirebase = onSaveToFirebase,
            modifier = Modifier.fillMaxSize(),
            onGoalDropped = onGoalDropped
        )
    }
}