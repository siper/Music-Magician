plugins {
    `android-module`
}

dependencies {
    implementation(libs.bundles.kotlin.coroutines)
    implementation(project(":core:data:server:core"))
    implementation(project(":core:data:server:deezer"))
    implementation(project(":core:data:server:itunes"))
    implementation(project(":core:data:server:lololyrics"))
    implementation(project(":core:data:server:cajunlyrics"))
    implementation(project(":core:data:server:chartlyrics"))
}