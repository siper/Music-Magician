plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Sdk.COMPILE_SDK

    defaultConfig {
        minSdk = Sdk.MIN_SDK
        targetSdk = Sdk.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}