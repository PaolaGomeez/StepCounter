package edu.sdgku.stepcounter.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.google.android.gms.wearable.Wearable
import com.google.firebase.firestore.ListenerRegistration
import edu.sdgku.stepcounter.presentation.theme.StepCounterTheme
import edu.sdgku.stepcounter.shared.data.FirebaseRepository

class MainActivity : ComponentActivity() {
    private var heartRate by mutableIntStateOf(72)
    private var stepsGoal by mutableIntStateOf(10000)

    private lateinit var wearDataListener: WearDataListener
    private lateinit var heartRateSensorManager: HeartRateSensorManager

    private lateinit var repository: FirebaseRepository
    private var firebaseListener: ListenerRegistration? = null

    private val heartRatePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            heartRateSensorManager.startListening()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        repository = FirebaseRepository()

        heartRateSensorManager = HeartRateSensorManager(
            context = this,
            onHeartRateChanged = { newHeartRate -> heartRate = newHeartRate }
        )

        if (heartRateSensorManager.hasHeartRateSensor && !heartRateSensorManager.hasPermission()) {
            heartRatePermissionLauncher.launch(heartRateSensorManager.requiredPermission)
        }

        // Data Layer listener (Assignment 2) - unchanged
        wearDataListener = WearDataListener(onStepsGoalChanged = { newGoal ->
            runOnUiThread { onNewGoal(newGoal, "phone") }
        })

        setContent {
            StepCounterTheme {
                WearFitnessApp(
                    heartRateSensorValue = heartRate,
                    hasHeartRateSensor = heartRateSensorManager.hasHeartRateSensor,
                    stepsGoalFromPhone = stepsGoal
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::heartRateSensorManager.isInitialized) {
            heartRateSensorManager.startListening()
        }
        if (::wearDataListener.isInitialized) {
            Wearable.getDataClient(this).addListener(wearDataListener)
        }
        startFirebaseListener()
    }

    override fun onPause() {
        super.onPause()
        if (::heartRateSensorManager.isInitialized) {
            heartRateSensorManager.stopListening()
        }
        if (::wearDataListener.isInitialized) {
            Wearable.getDataClient(this).removeListener(wearDataListener)
        }
        stopFirebaseListener()
    }

    private fun onNewGoal(newGoal: Int, source: String) {
        if (newGoal == stepsGoal) return
        stepsGoal = newGoal
        showNotification(
            context = this,
            notificationId = GOAL_NOTIFICATION_ID,
            title = "Fitness Data",
            message = "New steps goal from $source: $newGoal"
        )
    }

    // Cloud Firestore listener (Assignment 3)
    private fun startFirebaseListener() {
        if (firebaseListener != null || !::repository.isInitialized) return

        firebaseListener = repository.listenToFitnessData(
            onDataChanged = { fitnessData ->
                Log.d("SharedFirebaseWear", "Goal received from Firestore: ${fitnessData.dailyGoal}")
                runOnUiThread { onNewGoal(fitnessData.dailyGoal.toInt(), "Firebase") }
                            },
            onError = { exception ->
                Log.e("SharedFirebaseWear", "Firestore listener error", exception)
            }
        )
    }

    private fun stopFirebaseListener() {
        firebaseListener?.remove()
        firebaseListener = null
    }
}
