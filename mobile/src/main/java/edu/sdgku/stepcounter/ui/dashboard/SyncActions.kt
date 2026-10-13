package edu.sdgku.stepcounter.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.sdgku.stepcounter.R

@Composable
fun SyncActions(
    sendStatus: String,
    onSendToWatch: () -> Unit,
    onSaveToFirebase: () -> Unit,
    modifier: Modifier = Modifier,
    showsStatus: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onSendToWatch,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.send_to_watch))
        }

        Spacer(modifier = Modifier.height(DashboardDimens.smallSpacerHeight))

        Button(
            onClick = onSaveToFirebase,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.save_to_firebase))
        }

        if (showsStatus) {
            Spacer(modifier = Modifier.height(DashboardDimens.mediumSpacerHeight))
            Text(
                text = "${stringResource(id = R.string.status_prefix)} $sendStatus",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}