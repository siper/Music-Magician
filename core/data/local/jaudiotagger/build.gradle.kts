plugins {
    `android-module`
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.bundles.kotlin.coroutines)
    implementation(libs.bundles.koin)
    implementation("com.anggrayudi:storage:0.13.0")
    implementation(project(":core:data:local:core"))
}