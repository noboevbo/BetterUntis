pluginManagement {
	val kotlinVersion: String by settings
	val aboutlibrariesVersion: String by settings
	plugins {

		id("org.jetbrains.kotlin.plugin.compose") version kotlinVersion
		id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
		id("com.mikepenz.aboutlibraries.plugin") version aboutlibrariesVersion apply false
		id("com.google.devtools.ksp") version "2.1.0-1.0.29"
	}
}

include(":app", ":weekview", ":material-color-utils")
