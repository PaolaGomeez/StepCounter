package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.ExperimentalFoundationApi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalDropTarget(

    modifier: Modifier = Modifier,
    onGoalDropped: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    var isOver by remember { mutableStateOf(false) }

    val target = remember(key1 = onGoalDropped) {
        object : DragAndDropTarget {
            override fun onStarted(event: DragAndDropEvent) {
                isOver = false
            }
            override fun onEntered(event: DragAndDropEvent) {
                isOver = true
            }
            override fun onExited(event: DragAndDropEvent) {
                isOver = false
            }
            override fun onEnded(event: DragAndDropEvent) {
                isOver = false
            }
            override fun onDrop(event: DragAndDropEvent): Boolean {
                isOver = false
                val androidEvent = event.toAndroidDragEvent()
                val clipData = androidEvent.clipData
                if (clipData != null && clipData.itemCount > 0) {
                    val text = clipData.getItemAt(0).text?.toString()
                    val goalValue = text?.toIntOrNull()
                    if (goalValue != null) {
                        onGoalDropped(goalValue)
                        return true
                    }
                }
                return false
            }
        }
    }

    Box(
        modifier = modifier
            .dragAndDropTarget(
                shouldStartDragAndDrop = { true },
                target = target
            )
            .border(
                width = if (isOver) 3.dp else 1.dp,
                color = if (isOver) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .background(
                color = if (isOver) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                },
                shape = RoundedCornerShape(size = 12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}