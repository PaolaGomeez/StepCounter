package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.sdgku.stepcounter.shared.model.FitnessData

@Composable
fun DashboardInfoPane(
    sendStatus: String,
    cloudData: FitnessData?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cloud Document",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (cloudData != null) {
            Text(text = "Daily Goal: ${cloudData.dailyGoal}")
            Text(text = "Steps: ${cloudData.steps}")
            Text(text = "Heart Rate: ${cloudData.heartRate}")
        } else {
            Text(text = "No cloud data synchronized yet.")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Status: $sendStatus",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}