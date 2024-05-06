plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)

    id("com.google.devtools.ksp").version("1.8.22-1.0.11").apply(false)
    id("com.rickclephas.kmp.nativecoroutines").version("1.0.0-ALPHA-24").apply(false)
    alias(libs.plugins.androidTest) apply false
}
