package edu.sdgku.stepcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import edu.sdgku.stepcounter.shared.data.FirebaseRepository
import edu.sdgku.stepcounter.ui.dashboard.PhoneCompanionApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = FirebaseRepository()
        setContent {
            MaterialTheme {
                PhoneCompanionApp(repository = repository)
            }
        }
    }
}
