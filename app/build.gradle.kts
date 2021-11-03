plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 3

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "ru.stersh.musicmagician"
        minSdk = 21
        targetSdk = 31
        versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    lint {
        isAbortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":ui"))
    implementation(project(":feature:library:track"))
    implementation(project(":data:local:core"))
    implementation(project(":data:local:mediastore"))

    // Shimmer layout
    implementation(libs.shimmer)

    implementation(libs.bundles.kotlin.coroutines)

    implementation(libs.bundles.moxy)
    kapt(libs.moxy.compiler)

    // StorIO
    implementation("com.pushtorefresh.storio3:content-resolver:3.0.0")

    // AndroidX
    implementation("com.google.android.material:material:1.4.0")
    implementation(libs.androidx.appcompat)
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation("androidx.preference:preference-ktx:1.1.1")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // Kotlin std
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")

    // OkHttp 4
    implementation("com.squareup.okhttp3:okhttp:4.9.2")

    // Retrofit 2
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.8")

    // Koin
    implementation(libs.bundles.koin)

    // AdapterDelegates 4
    val adapterDelegatesVersion = "4.3.0"
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$adapterDelegatesVersion")
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:$adapterDelegatesVersion")

    // Cicerone
    implementation(libs.cicerone)

    // Calligraphy 3
    implementation("io.github.inflationx:calligraphy3:3.1.1")
    implementation("io.github.inflationx:viewpump:2.0.3")

    // Leak Canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    // Timber
    implementation(libs.timber)

    testImplementation("junit:junit:4.13.2")
}
