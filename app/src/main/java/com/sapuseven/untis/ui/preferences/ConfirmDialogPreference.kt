package com.sapuseven.untis.ui.preferences

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.sapuseven.untis.R
import com.sapuseven.untis.preferences.UntisPreferenceDataStore

@Composable
fun ConfirmDialogPreference(
	title: (@Composable () -> Unit),
	dialogTitle: (@Composable () -> Unit) = title,
	summary: (@Composable () -> Unit)? = null,
	dialogText: (@Composable () -> Unit)? = summary,
	icon: (@Composable () -> Unit)? = null,
	dependency: UntisPreferenceDataStore<*>? = null,
	onConfirm: () -> Unit = {}
) {
	var showDialog by remember { mutableStateOf(false) }

	Preference(
		title = title,
		summary = summary,
		icon = icon,
		dependency = dependency,
		dataStore = UntisPreferenceDataStore.emptyDataStore(),
		onClick = { showDialog = true }
	)

	if (showDialog) {
		AlertDialog(
			onDismissRequest = { showDialog = false },
			title = dialogTitle,
			text = dialogText,
			confirmButton = {
				TextButton(
					onClick = {
						showDialog = false
						onConfirm()
					}) {
					Text(stringResource(id = R.string.all_ok))
				}
			},
			dismissButton = {
				TextButton(onClick = { showDialog = false }) {
					Text(stringResource(id = R.string.all_cancel))
				}
			}
		)
	}
}
