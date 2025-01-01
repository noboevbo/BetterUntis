val kotlin_version = "1.9.24"

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.sapuseven.untis.views.weekview"

    defaultConfig {
        minSdk = 21
        testOptions.targetSdk = 35
        compileSdk = 35
    }

    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("joda-time:joda-time:2.10.14")
    implementation("androidx.compose.material3:material3:1.0.0-alpha15")
    implementation("androidx.compose.material:material:1.1.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}