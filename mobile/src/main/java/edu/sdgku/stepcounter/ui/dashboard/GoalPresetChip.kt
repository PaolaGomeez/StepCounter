package edu.sdgku.stepcounter.ui.dashboard

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalPresetChip(value: Int, modifier: Modifier = Modifier) {
    val text = value.toString()
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .dragAndDropSource {
                detectTapGestures(
                    onLongPress = {
                        startTransfer(
                            DragAndDropTransferData(
                                clipData = ClipData.newPlainText("goal", text)
                            )
                        )
                    }
                )
            }
    )
}