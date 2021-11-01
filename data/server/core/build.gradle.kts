plugins {
    `android-module`
    kotlin("kapt")
    alias(libs.plugins.ksp)
}

dependencies {
    api(libs.network.okhttp)
    api(libs.bundles.network.retrofit)
    api(libs.bundles.koin)
    api(libs.moshi.core)
    kapt(libs.moshi.codegen)
    ksp(libs.moshi.codegen)
}