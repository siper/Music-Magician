plugins {
    `android-module`
}

dependencies {
    api(project(":core:data:local:core"))
    api(project(":core:utils"))
    api(project(":core:ui"))
    api(project(":core:navigation"))
    api(libs.shimmer)
    api(libs.timber)
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