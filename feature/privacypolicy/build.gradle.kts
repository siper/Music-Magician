plugins {
    `android-module`
}

dependencies {
    implementation(project(":core:ui"))
    implementation(libs.cicerone)
    implementation(libs.bundles.koin)
}