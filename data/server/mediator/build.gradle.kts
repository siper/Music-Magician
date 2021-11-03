plugins {
    `android-module`
}

dependencies {
    implementation(libs.bundles.kotlin.coroutines)
    implementation(project(":data:server:core"))
    implementation(project(":data:server:deezer"))
    implementation(project(":data:server:itunes"))
    implementation(project(":data:server:lololyrics"))
    implementation(project(":data:server:cajunlyrics"))
    implementation(project(":data:server:chartlyrics"))
}