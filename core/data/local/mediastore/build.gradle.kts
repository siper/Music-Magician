plugins {
    `android-module`
}

dependencies {
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.koin)
    implementation(project(":core:data:local:core"))
}