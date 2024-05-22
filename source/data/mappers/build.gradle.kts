plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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
        androidMain.dependencies {
            implementation(projects.source.telegram.tdlibAndroid)
        }

        jvmMain.dependencies {
            implementation(projects.source.telegram.tdlibDesktop)
        }

        commonMain.dependencies {
            implementation(projects.source.feature.authApi)
            implementation(projects.source.feature.filesApi)
            implementation(projects.source.feature.mainApi)
            implementation(projects.source.utils)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive.mappers"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
