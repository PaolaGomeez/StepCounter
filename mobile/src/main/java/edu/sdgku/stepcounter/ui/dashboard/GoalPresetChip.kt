package edu.sdgku.stepcounter.ui.dashboard

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalPresetChip(value: Int, modifier: Modifier = Modifier) {
    val text = value.toString()
    AssistChip(
        onClick = {},
        label = { Text(text) },
        modifier = modifier.dragAndDropSource {
            detectTapGestures(
                onLongPress = {
                    startTransfer(
                        DragAndDropTransferData(
                            clipData = ClipData(
                                text,
                                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                                ClipData.Item(text)
                            )
                        )
                    )
                }
            )
        }
    )
}
