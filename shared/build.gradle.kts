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
    
    jvm()

    sourceSets {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "TGDriveKit"
                isStatic = true

                export(projects.source.root)
                export(projects.source.data.database)
                export(projects.source.data.mappers)
                export(projects.source.data.network)
                export(projects.source.feature.auth)
                export(projects.source.feature.authApi)
                export(projects.source.feature.favorites)
                export(projects.source.feature.favoritesApi)
                export(projects.source.feature.files)
                export(projects.source.feature.filesApi)
                export(projects.source.feature.main)
                export(projects.source.feature.mainApi)
                export(projects.source.feature.settings)
                export(projects.source.feature.settingsApi)
                export(projects.source.telegram.tdlibKtx)
                export(projects.source.utils)

                export(libs.decompose)
                export(libs.mvikotlin)
                export(libs.mvikotlin.main)
                export(libs.essenty.lifecycle)
            }
        }

        commonMain.dependencies {
            api(projects.source.root)
            api(projects.source.data.database)
            api(projects.source.data.mappers)
            api(projects.source.data.network)
            api(projects.source.feature.auth)
            api(projects.source.feature.authApi)
            api(projects.source.feature.favorites)
            api(projects.source.feature.favoritesApi)
            api(projects.source.feature.files)
            api(projects.source.feature.filesApi)
            api(projects.source.feature.main)
            api(projects.source.feature.mainApi)
            api(projects.source.feature.settings)
            api(projects.source.feature.settingsApi)
            api(projects.source.telegram.tdlibKtx)
            api(projects.source.utils)

            api(libs.decompose)
            api(libs.mvikotlin)
            api(libs.mvikotlin.main)
            api(libs.essenty.lifecycle)
        }
    }
}

android {
    namespace = "com.voxeldev.tgdrive.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
