plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
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
            implementation(libs.koin)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)

            implementation(libs.room.runtime)
            implementation(libs.room.sqlite)

            implementation(projects.source.feature.favoritesApi)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

dependencies {
    add(configurationName = "kspAndroid", libs.room.compiler)
    add(configurationName = "kspIosX64", libs.room.compiler)
    add(configurationName = "kspIosArm64", libs.room.compiler)
    add(configurationName = "kspIosSimulatorArm64", libs.room.compiler)
    add(configurationName = "kspJvm", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
