buildscript {
	val kotlinVersion: String by project

	repositories {
		google()
		mavenCentral()
	}

	dependencies {
		classpath("com.android.tools.build:gradle:8.7.3")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
		classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")

		// NOTE: Do not place your application dependencies here; they belong
		// in the individual module build.gradle files
	}
}

plugins {
	id("org.jetbrains.kotlin.plugin.serialization")
	id("com.mikepenz.aboutlibraries.plugin")
}

allprojects {
	repositories {
		google()
		mavenCentral()
		maven {
			url = uri("https://jitpack.io")
		}
	}
}

tasks.register<Delete>("clean") {
	delete(layout.buildDirectory)
}