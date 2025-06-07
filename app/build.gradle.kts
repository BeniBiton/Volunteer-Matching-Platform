import java.util.Properties
import java.io.File

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.volunteer_matching_platform"
    compileSdk = 35
    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "secret.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }

    defaultConfig {
        applicationId = "com.example.volunteer_matching_platform"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "PLACES_API_KEY",
            "\"" + localProperties.getProperty("PLACES_API_KEY") + "\""
        )

    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "PLACES_API_KEY",
                "\"" + localProperties.getProperty("PLACES_API_KEY") + "\""
            )
        }
        debug {
            buildConfigField(
                "String",
                "PLACES_API_KEY",
                "\"" + localProperties.getProperty("PLACES_API_KEY") + "\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
        resValues = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.common)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.libraries.places:places:3.3.0")
    implementation("com.google.firebase:firebase-bom:32.8.1")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-auth")
}