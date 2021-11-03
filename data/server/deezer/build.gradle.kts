plugins {
    `android-module`
}

dependencies {
    implementation(libs.network.retrofit.converter.moshi)
    implementation(project(":data:server:core"))
}