plugins {
    `android-module`
}

dependencies {
    api(project(":feature:editor:core"))
    implementation(project(":core:data:server:core"))
}