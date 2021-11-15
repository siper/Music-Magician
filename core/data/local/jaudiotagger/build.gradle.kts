plugins {
    `android-module`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.koin)
    implementation(libs.timber)
    implementation(project(":core:data:local:core"))
}