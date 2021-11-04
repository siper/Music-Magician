plugins {
    `android-module`
}

dependencies {
    implementation(libs.network.retrofit.converter.moshi)
    implementation(project(":core:data:server:core"))
}