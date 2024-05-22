plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.decompose)
            implementation(libs.decompose.extensions)
            implementation(libs.mvikotlin)
            implementation(libs.mvikotlin.main)
            implementation(libs.essenty.lifecycle)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.koin.core)

            implementation(libs.kotlinx.coroutines.core)

            implementation(projects.source.feature.auth)
            implementation(projects.source.feature.main)
            implementation(projects.source.feature.filesApi)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive.root"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
