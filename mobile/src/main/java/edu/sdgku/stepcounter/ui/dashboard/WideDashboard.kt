package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.sdgku.stepcounter.shared.model.FitnessData

@Composable
fun WideDashboard(
    stepsGoal: Int,
    sendStatus: String,
    layoutLabel: String,
    cloudData: FitnessData?,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onSendToWatch: () -> Unit,
    onSaveToFirebase: () -> Unit,
    extraEditorContent: @Composable () -> Unit = {},
    onGoalDropped: ((Int) -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp)
    ) {
        Text(
            text = "Layout: $layoutLabel",
            style = MaterialTheme.typography.labelLarge
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            GoalEditorPane(
                stepsGoal = stepsGoal,
                sendStatus = sendStatus,
                showStatusHere = false,
                onDecrease = onDecrease,
                onIncrease = onIncrease,
                onSendToWatch = onSendToWatch,
                onSaveToFirebase = onSaveToFirebase,
                modifier = Modifier.weight(DashboardDimens.paneWeightEditor),
                extraContent = extraEditorContent,
                onGoalDropped = onGoalDropped
            )

            VerticalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            DashboardInfoPane(
                sendStatus = sendStatus,
                cloudData = cloudData,
                modifier = Modifier.weight(DashboardDimens.paneWeightInspector)
            )
        }
    }
}