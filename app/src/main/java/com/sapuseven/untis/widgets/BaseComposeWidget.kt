package com.sapuseven.untis.widgets

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.background
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.Text
import com.sapuseven.untis.R
import com.sapuseven.untis.data.databases.UserDatabase
import com.sapuseven.untis.helpers.config.intDataStore
import com.sapuseven.untis.ui.preferences.materialColors
import com.sapuseven.untis.ui.theme.generateColorScheme
import com.sapuseven.untis.ui.widgets.WidgetListItemModel
import com.sapuseven.untis.ui.widgets.WidgetListView
import com.sapuseven.untis.ui.widgets.WidgetListViewHeader
import kotlinx.coroutines.runBlocking

open class BaseComposeWidget : GlanceAppWidget() {
	companion object {
		const val PREFERENCE_KEY_LONG_USER = "userId"
		const val PREFERENCE_KEY_INT_ELEMENT_ID = "elementId"
		const val PREFERENCE_KEY_STRING_ELEMENT_TYPE = "elementType"
	}

	private var items by mutableStateOf<List<WidgetListItemModel>?>(null)

	private var elementId by mutableStateOf<Int>(-1)

	@Composable
	fun AppWidgetTheme(
		userId: Long,
		content: @Composable (colorSchemeDark: ColorScheme, colorSchemeLight: ColorScheme) -> Unit
	) {
		val context = LocalContext.current

		val themeColorPref = context.intDataStore(
			userId,
			"preference_theme_color",
			defaultValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
				with(LocalContext.current) {
					resources.getColor(android.R.color.system_accent1_500, theme)
				}
			else
				materialColors[0].toArgb()
		)

		val themeColor = runBlocking { Color(themeColorPref.getValue()) }

		content(
			generateColorScheme(
				context = context,
				themeColor == Color(themeColorPref.defaultValue),
				themeColor,
				darkTheme = true,
				false // Ignore for now
			),
			generateColorScheme(
				context = context,
				themeColor == Color(themeColorPref.defaultValue),
				themeColor,
				darkTheme = false,
				false // Ignore for now
			)
		)
	}

	@Composable
	private fun Content() {
		val prefs = currentState<Preferences>()
		val userId = prefs[longPreferencesKey(PREFERENCE_KEY_LONG_USER)] ?: -1
		elementId = prefs[intPreferencesKey(PREFERENCE_KEY_INT_ELEMENT_ID)] ?: -1

		val userDatabase = UserDatabase.getInstance(LocalContext.current)
		val user = userDatabase.userDao().getById(userId)

		AppWidgetTheme(userId) { colorSchemeDark, colorSchemeLight ->
			val onSurface = ColorProvider(
				day = colorSchemeLight.onSurface,
				night = colorSchemeDark.onSurface,
			)

			Column(
				modifier = GlanceModifier
					.fillMaxSize()
					.background(
						day = colorSchemeLight.surface,
						night = colorSchemeDark.surface
					)
					.appWidgetBackground()
			) {
				WidgetListViewHeader(
					modifier = GlanceModifier
						.fillMaxWidth(),
					dayColorScheme = colorSchemeLight,
					nightColorScheme = colorSchemeDark,
					headlineContent = user?.getDisplayedName(LocalContext.current)
						?: "(Invalid user)",
					supportingContent = user?.userData?.schoolName
				)


				items?.let {
					WidgetListView(
						modifier = GlanceModifier
							.fillMaxWidth()
							.defaultWeight(),
						dayColorScheme = colorSchemeLight,
						nightColorScheme = colorSchemeDark,
						onClickAction = actionRunCallback<TimetableItemActionCallback>(),
						items = it
					)
				} ?: run {
					Box(
						modifier = GlanceModifier
							.fillMaxWidth()
							.defaultWeight(),
						contentAlignment = Alignment.Center
					) {
						Text(
							LocalContext.current.getString(R.string.loading),
							style = MaterialTheme.typography.bodyLarge.toGlanceTextStyle(onSurface)
						)
					}
				}
			}
		}
	}

	fun setData(data: List<WidgetListItemModel>) {
		items = data
	}

	override suspend fun provideGlance(context: Context, id: GlanceId) {
		provideContent {
			GlanceTheme {
				Content()
			}
		}
	}
}
