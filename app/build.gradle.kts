import com.mikepenz.aboutlibraries.plugin.DuplicateMode

plugins {
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aboutLibraries)
    id("com.android.application")
    id("de.mannodermaus.android-junit5")
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
        minSdk = 24
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.serialization.cbor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.fuel)
    implementation(libs.fuel.coroutines)
    implementation(libs.fuel.kotlinx.serialization)
    implementation(libs.joda.time)
    implementation(libs.google.material)
    implementation(libs.colormath)
    implementation(libs.zxing.android.embedded)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.animation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.google.material)
    implementation(libs.compose.theme.adapter)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.work.runtime.ktx)
    implementation(libs.coil.compose)
    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.compose)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    ksp(libs.room.compiler)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.vintage.engine)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.hamcrest)
    testImplementation(libs.hamcrest.library)
    androidTestImplementation(libs.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    implementation(project(":weekview"))
    implementation(project(":material-color-utils"))
}
