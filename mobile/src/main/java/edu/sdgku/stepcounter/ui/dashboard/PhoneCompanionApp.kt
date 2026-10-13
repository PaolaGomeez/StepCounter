package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.sdgku.stepcounter.R
import edu.sdgku.stepcounter.sendStepsGoalToWatch
import edu.sdgku.stepcounter.shared.data.FirebaseRepository
import edu.sdgku.stepcounter.shared.model.FitnessData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneCompanionApp(repository: FirebaseRepository) {
    val context = LocalContext.current
    var stepsGoal by remember { mutableIntStateOf(10000) }
    var sendStatus by remember { mutableStateOf("Not Sent") }
    var cloudData by remember { mutableStateOf<FitnessData?>(null) }

    // WindowSizeClass: decides phone (Column) vs tablet (Row)
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isWide = windowSizeClass.isWide()
    val layoutLabel = windowSizeClass.label()

    DisposableEffect(key1 = repository) {
        val listenerRegistration = repository.listenToFitnessData(
            onDataChanged = { fitnessData ->
                cloudData = fitnessData
                stepsGoal = fitnessData.dailyGoal.toInt()
                sendStatus = "Goal received from Firebase: $stepsGoal"
            },
            onError = { exception ->
                sendStatus = "Firebase error: " + (exception.message ?: "Unknown error")
            }
        )
        onDispose { listenerRegistration.remove() }
    }

    val onDecrease = { if (stepsGoal > 500) stepsGoal -= 500 }
    val onIncrease = { stepsGoal += 500 }

    val onGoalDropped: (Int) -> Unit = { dropped ->
        stepsGoal = dropped.coerceAtLeast(minimumValue = 500)
        sendStatus = "Dropped preset $dropped (not saved yet)"
    }

    val onSendToWatch = {
        sendStatus = "Sending..."
        sendStepsGoalToWatch(
            context = context,
            stepsGoal = stepsGoal,
            onSuccess = { sendStatus = "Sent $stepsGoal to the watch" },
            onError = { errorMessage -> sendStatus = "Error: $errorMessage" }
        )
    }

    val onSaveToFirebase = {
        sendStatus = "Saving to Firebase..."
        repository.updateDailyGoal(
            dailyGoal = stepsGoal.toLong(),
            onSuccess = { sendStatus = "Saved $stepsGoal in Firebase" },
            onError = { exception ->
                sendStatus = "Firebase error: " + (exception.message ?: "Unknown error")
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    Text(text = layoutLabel, modifier = Modifier.padding(end = 16.dp))
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isWide) {
                WideDashboard(
                    stepsGoal = stepsGoal,
                    sendStatus = sendStatus,
                    layoutLabel = layoutLabel,
                    cloudData = cloudData,
                    onDecrease = onDecrease,
                    onIncrease = onIncrease,
                    onSendToWatch = onSendToWatch,
                    onSaveToFirebase = onSaveToFirebase,
                    extraEditorContent = { GoalPresetRow(modifier = Modifier.padding(top = 16.dp)) },
                    onGoalDropped = onGoalDropped
                )
            } else {
                CompactDashboard(
                    stepsGoal = stepsGoal,
                    sendStatus = sendStatus,
                    layoutLabel = layoutLabel,
                    onDecrease = onDecrease,
                    onIncrease = onIncrease,
                    onSendToWatch = onSendToWatch,
                    onSaveToFirebase = onSaveToFirebase,
                    onGoalDropped = onGoalDropped
                )
            }
        }
    }
}
