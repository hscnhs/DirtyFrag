plugins {
    id("com.android.application")
}

android {
    namespace = "df.root"
    compileSdk = 37

    defaultConfig {
        applicationId = "df.root"
        minSdk = 30
        targetSdk = 37
        versionCode = 34
        versionName = "1.17"

        ndk {
            abiFilters += listOf("arm64-v8a")
        }
    }

    signingConfigs {}

    buildTypes {
        debug {}
    }

// AGP 9 removed the old applicationVariants API; this is the replacement.
androidComponents {
    onVariants { variant ->
        variant.outputs.forEach { output ->
            output.outputFileName.set("dirtyfrag.apk")
        }
    }
}
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        checkReleaseBuilds = false
    }

    buildFeatures {
        // No viewbinding: df.root.ActivityMainBinding is written by hand, which keeps
        // every binding.<id> call site and drops androidx.databinding:viewbinding.
        viewBinding = false
        // The toolbar version pill reads BuildConfig.VERSION_NAME, so the pill
        // can never fall behind versionName again.
        buildConfig = true
    }

    externalNativeBuild {
        cmake {
            path("src/main/jni/CMakeLists.txt")
        }
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {
    // Deliberately empty. The whole UI is framework widgets plus the custom views in
    // df.root (CardStackLayout, DraggableCardView, PillButton, OneUiSwitch,
    // TwoStepProgressView, BottomFadeView). appcompat, material, constraintlayout and
    // viewbinding used to sit here and pulled 40 androidx/material artifacts into the
    // APK; the replacements are documented under "Unreleased" in CHANGELOG.md.
}
