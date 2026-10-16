package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import edu.sdgku.stepcounter.shared.model.FitnessData

@Preview(name = "Compact Phone", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
private fun CompactDashboardPreview() {
    MaterialTheme {
        CompactDashboard(
            stepsGoal = 10000,
            sendStatus = "Not sent",
            layoutLabel = "Compact",
            onDecrease = {},
            onIncrease = {},
            onSendToWatch = {},
            onSaveToFirebase = {}
        )
    }
}

@Preview(name = "Wide Tablet", widthDp = 1280, heightDp = 800, showBackground = true)
@Composable
private fun WideDashboardPreview() {
    MaterialTheme {
        WideDashboard(
            stepsGoal = 12000,
            sendStatus = "Dropped preset 12000 (not saved yet)",
            layoutLabel = "Expanded",
            cloudData = FitnessData(dailyGoal = 11000, steps = 0, heartRate = 72),
            onDecrease = {},
            onIncrease = {},
            onSendToWatch = {},
            onSaveToFirebase = {},
            extraEditorContent = { GoalPresetRow() },
            onGoalDropped = {}
        )
    }
}