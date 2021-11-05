plugins {
    `android-module`
    kotlin("kapt")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.network.retrofit.converter.moshi)
    kapt(libs.moshi.codegen)
    ksp(libs.moshi.codegen)
    implementation(project(":core:data:server:core"))
}