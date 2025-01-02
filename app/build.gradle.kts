import com.mikepenz.aboutlibraries.plugin.DuplicateMode

plugins {
	id("org.jetbrains.kotlin.plugin.compose")
	id("com.android.application")
	id("com.mikepenz.aboutlibraries.plugin")
	id("de.mannodermaus.android-junit5")
	id("com.google.devtools.ksp")
	kotlin("android")
	kotlin("plugin.serialization")
}

// Auto-generates a new version code every minute
fun generateVersionCode(): Int {
	return (System.currentTimeMillis() / 1000 / 60).toInt()
}

android {
	namespace = "com.sapuseven.untis"

	androidResources {
		generateLocaleConfig = true
	}

	defaultConfig {
		applicationId = "com.sapuseven.untis"
		minSdk = 21
		targetSdk = 35
		compileSdk = 35
		versionCode = generateVersionCode()
		versionName = "4.2.0-beta01"
		vectorDrawables.useSupportLibrary = true
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		ksp {
			arg("room.schemaLocation", "${projectDir}/schemas".toString())
		}
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}

		getByName("debug") {
			isMinifyEnabled = false
			isShrinkResources = false
			applicationIdSuffix = ".debug"
			versionNameSuffix = "-DEBUG"
		}
	}

	buildFeatures {
		compose = true
		buildConfig = true
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
		isCoreLibraryDesugaringEnabled = true
	}

	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
	}

	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.14"
	}

	lint.disable += "MissingTranslation"
	flavorDimensions += "dependencies"

	productFlavors {
		create("foss") {
			isDefault = true
			dimension = "dependencies"
		}
	}
}

aboutLibraries {
	includePlatform = false
	duplicationMode = DuplicateMode.MERGE
}

dependencies {
	val kotlinVersion: String by project
	val serializationVersion: String by project
	val fuelVersion: String by project
	val composeVersion: String by project
	val roomVersion: String by project
	val accompanistVersion: String by project
	val aboutlibrariesVersion: String by project
	val junitVersion: String by project

	implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
	implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:${serializationVersion}")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${serializationVersion}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("androidx.constraintlayout:constraintlayout:2.2.0")
	implementation("androidx.fragment:fragment-ktx:1.8.5")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
	implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
	implementation("androidx.recyclerview:recyclerview:1.3.2")
	implementation("androidx.preference:preference-ktx:1.2.1")
	implementation("com.github.kittinunf.fuel:fuel:${fuelVersion}")
	implementation("com.github.kittinunf.fuel:fuel-coroutines:${fuelVersion}")
	implementation("com.github.kittinunf.fuel:fuel-kotlinx-serialization:${fuelVersion}")
	implementation("joda-time:joda-time:2.10.14")
	implementation("com.google.android.material:material:1.12.0")
	implementation("com.github.ajalt.colormath:colormath:3.2.0")
	implementation("com.journeyapps:zxing-android-embedded:4.3.0")
	implementation("androidx.activity:activity-compose:1.9.3")
	implementation("androidx.compose.animation:animation:${composeVersion}")
	implementation("androidx.compose.ui:ui:${composeVersion}")
	implementation("androidx.compose.ui:ui-tooling:${composeVersion}")
	implementation("androidx.compose.material3:material3:1.3.1")
	implementation("androidx.glance:glance-appwidget:1.1.1")
	implementation("androidx.navigation:navigation-compose:2.8.5")
	implementation("androidx.datastore:datastore-preferences:1.1.1")
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
	implementation("androidx.room:room-runtime:${roomVersion}")
	implementation("androidx.room:room-ktx:${roomVersion}")
	implementation("com.google.android.material:material:1.12.0")
	implementation("com.google.android.material:compose-theme-adapter:1.2.1")
	implementation("com.google.accompanist:accompanist-swiperefresh:${accompanistVersion}")
	implementation("com.google.accompanist:accompanist-systemuicontroller:${accompanistVersion}")
	implementation("com.google.accompanist:accompanist-pager:${accompanistVersion}")
	implementation("com.google.accompanist:accompanist-pager-indicators:${accompanistVersion}")
	implementation("com.google.accompanist:accompanist-flowlayout:${accompanistVersion}")
    implementation("androidx.work:work-runtime-ktx:2.10.0")
	implementation("io.coil-kt:coil-compose:2.2.2")
	implementation("com.mikepenz:aboutlibraries-core:${aboutlibrariesVersion}")
	implementation("com.mikepenz:aboutlibraries-compose:${aboutlibrariesVersion}")

	coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")

	ksp("androidx.room:room-compiler:${roomVersion}")

	testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
	testImplementation("org.junit.jupiter:junit-jupiter-params:${junitVersion}")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
	testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${junitVersion}")

	testImplementation("junit:junit:4.13.2")
	testImplementation("io.mockk:mockk:1.13.4")
	testImplementation("org.mockito:mockito-core:4.6.1")
	testImplementation("org.hamcrest:hamcrest:2.2")
	testImplementation("org.hamcrest:hamcrest-library:2.2")
	androidTestImplementation("androidx.test:core:1.6.1")
	androidTestImplementation("androidx.compose.ui:ui-test-junit4:${composeVersion}")
	debugImplementation("androidx.compose.ui:ui-test-manifest:${composeVersion}")


	implementation(project(":weekview"))
	implementation(project(":material-color-utils"))
}
