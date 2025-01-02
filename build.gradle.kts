buildscript {
	val kotlinVersion: String by project

	repositories {
		google()
		mavenCentral()
	}

	dependencies {
		classpath(libs.gradle)
		classpath(libs.kotlin.gradle.plugin)
		classpath(libs.android.junit5)

		// NOTE: Do not place your application dependencies here; they belong
		// in the individual module build.gradle files
	}
}

plugins {
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.aboutLibraries)
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