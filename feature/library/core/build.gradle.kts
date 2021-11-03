plugins {
    `android-module`
}

dependencies {
    api(project(":data:local:core"))
    implementation(project(":ui"))
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.appcompat)
    api(libs.bundles.kotlin.coroutines)
    api(libs.bundles.koin)
    api(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.recyclerview.core)
    implementation(libs.bundles.adapterdelegates)
    implementation(libs.androidx.constraintlayout)
}