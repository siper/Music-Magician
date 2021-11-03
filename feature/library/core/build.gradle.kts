plugins {
    `android-module`
}

dependencies {
    api(project(":data:local:core"))
    api(project(":ui"))
    api(libs.shimmer)
    api(libs.cicerone)
    api(libs.coil.core)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.appcompat)
    api(libs.bundles.kotlin.coroutines)
    api(libs.bundles.koin)
    api(libs.bundles.adapterdelegates)
    api(libs.androidx.lifecycle.runtime)
    api(libs.androidx.recyclerview.core)
    api(libs.androidx.constraintlayout)
}