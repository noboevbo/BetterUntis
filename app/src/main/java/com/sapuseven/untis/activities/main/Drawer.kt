package com.sapuseven.untis.activities.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sapuseven.untis.R
import com.sapuseven.untis.activities.InfoCenterActivity
import com.sapuseven.untis.activities.RoomFinderActivity
import com.sapuseven.untis.activities.SettingsActivity
import com.sapuseven.untis.helpers.timetable.TimetableDatabaseInterface
import com.sapuseven.untis.models.untis.timetable.PeriodElement
import com.sapuseven.untis.ui.models.NavItemShortcut
import com.sapuseven.untis.ui.models.NavItemTimetable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerItems(
	isMessengerAvailable: Boolean = false,
	disableTypeSelection: Boolean = false,
	displayedElement: PeriodElement? = null,
	onTimetableClick: (item: NavItemTimetable) -> Unit,
	onShortcutClick: (item: NavItemShortcut) -> Unit,
) {
	val navItemsElementTypes = listOf(
		NavItemTimetable(
			id = 1,
			icon = painterResource(id = R.drawable.all_classes),
			label = stringResource(id = R.string.all_classes),
			elementType = TimetableDatabaseInterface.Type.CLASS
		),
		NavItemTimetable(
			id = 2,
			icon = painterResource(id = R.drawable.all_teachers),
			label = stringResource(id = R.string.all_teachers),
			elementType = TimetableDatabaseInterface.Type.TEACHER
		),
		NavItemTimetable(
			id = 3,
			icon = painterResource(id = R.drawable.all_rooms),
			label = stringResource(id = R.string.all_rooms),
			elementType = TimetableDatabaseInterface.Type.ROOM
		),
	)

	var navItemsShortcuts = listOf(
		NavItemShortcut(
			id = 1,
			icon = painterResource(id = R.drawable.all_infocenter),
			label = stringResource(id = R.string.activity_title_info_center),
			InfoCenterActivity::class.java
		),
		NavItemShortcut(
			id = 2,
			icon = painterResource(id = R.drawable.all_search_rooms),
			label = stringResource(id = R.string.activity_title_free_rooms),
			RoomFinderActivity::class.java
		),
		NavItemShortcut(
			id = 4,
			icon = painterResource(id = R.drawable.all_settings),
			label = stringResource(id = R.string.activity_title_settings),
			SettingsActivity::class.java
		)
	)
	if (isMessengerAvailable) {
		navItemsShortcuts = navItemsShortcuts.plus(
			NavItemShortcut(
				id = 3,
				icon = painterResource(id = R.drawable.all_messenger),
				label = stringResource(id = R.string.activity_title_messenger),
				null
			)
		).sortedBy {
			it.id
		}
	}

	navItemsElementTypes.forEach { item ->
		NavigationDrawerItem(
			icon = { Icon(item.icon, contentDescription = null) },
			label = { Text(item.label) },
			selected = !disableTypeSelection && item.elementType.name == displayedElement?.type,
			onClick = { onTimetableClick(item) },
			modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
		)
	}

	DrawerDivider()

	navItemsShortcuts.forEach { item ->
		NavigationDrawerItem(
			icon = { Icon(item.icon, contentDescription = null) },
			label = { Text(item.label) },
			selected = false,
			onClick = { onShortcutClick(item) },
			modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
		)
	}
}

@Composable
fun DrawerDivider() {
	HorizontalDivider(
		color = MaterialTheme.colorScheme.outline,
		modifier = Modifier.padding(vertical = 8.dp)
	)
}

@Composable
fun DrawerText(text: String) {
	Text(
		text = text,
		style = MaterialTheme.typography.labelMedium,
		modifier = Modifier.padding(start = 28.dp, top = 16.dp, bottom = 8.dp)
	)
}
