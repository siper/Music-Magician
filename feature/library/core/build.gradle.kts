plugins {
    `android-module`
    kotlin("kapt")
}

dependencies {
    api(project(":data:local:core"))
    api(libs.bundles.moxy)
    api(libs.androidx.appcompat)
    kapt(libs.moxy.compiler)
    api(libs.bundles.kotlin.coroutines)
}