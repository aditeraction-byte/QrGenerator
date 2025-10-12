import org.gradle.kotlin.dsl.androidTestImplementation
import org.gradle.kotlin.dsl.kaptAndroidTest
import org.gradle.kotlin.dsl.testImplementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.compose") version "2.1.0"
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services)
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
    javacOptions {
        option("-Xlanguage-version", "2.1")
    }
}

android {
    namespace = "com.example.qrgenerator"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.qrgenerator"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // CORE / KOTLIN
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // COMPOSE
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // NAVIGATION COMPOSE
    implementation(libs.androidx.navigation.compose)

    // COROUTINES
    implementation(libs.kotlinx.coroutines.android)

    // HILT / DI
    implementation(libs.hilt.android)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.ui.test.junit4.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // FIREBASE (con BOM)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    // ZXING
    implementation(libs.zxing.core)
    implementation(libs.zxing.android.embedded)

    // TESTING
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // DEBUG / TOOLING
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // MOCKING
    testImplementation(libs.mockk)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation ("io.mockk:mockk-android:1.13.8")
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.48") // versión Hilt que uses
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.48")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    implementation ("com.patrykandpatrick.vico:compose:1.14.0")
    implementation ("com.patrykandpatrick.vico:core:1.14.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
}