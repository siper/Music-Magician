plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

dependencies  {
    implementation("com.android.tools.build:gradle:7.1.0-alpha01")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
}