plugins {
    id("java-library")
}

dependencies {
    implementation(libs.error.prone.annotations)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
