plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.sapuseven.untis.views.weekview"

    defaultConfig {
        minSdk = 21
        testOptions.targetSdk = 35
        compileSdk = 35
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

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
    implementation(libs.kotlin.stdlib)
    implementation(libs.joda.time)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}