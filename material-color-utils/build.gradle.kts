plugins {
    id("java-library")
}

dependencies {
    implementation("com.google.errorprone:error_prone_annotations:2.15.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}
