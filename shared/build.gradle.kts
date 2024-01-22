plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    kotlin("plugin.serialization") version "1.9.21"
    id("com.squareup.sqldelight").version("1.5.5")

    id("com.google.devtools.ksp")
    id("com.rickclephas.kmp.nativecoroutines")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.precompose)
            api(libs.precompose.viewmodel) // For ViewModel integration
            api(libs.precompose.koin) // For Koin integration

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.runtime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.android.driver)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.native.driver)
        }
    }
}

android {
    namespace = "com.tailoredapps.bookodyssee_km"
    compileSdk = 34
    defaultConfig {
        minSdk = 31
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}

sqldelight {
    database("BookOdysseeDb") {
        packageName = "com.tailoredapps.bookodyssee_km.cache"
    }
}