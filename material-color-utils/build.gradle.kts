plugins {
    id("java-library")
}

dependencies {
    implementation("com.google.errorprone:error_prone_annotations:2.18.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
