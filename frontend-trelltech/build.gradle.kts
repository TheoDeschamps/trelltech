// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
}

buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion("1.8.20")
                because("Force Kotlin 1.8.20 to match Compose compiler compatibility")
            }
        }
    }
}