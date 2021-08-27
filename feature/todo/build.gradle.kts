plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.SAFE_ARGS)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT)
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = AndroidConfig.KOTLIN_OPTION_JVM_TARGET
    }
}

dependencies {
    implementation(project(":shared"))

    api(libs.bundles.ktx)
    api(libs.appcompat)
    api(libs.material)
    api(libs.cardview)
    api(libs.lottie)
    api(libs.bundles.navigation)
    api(libs.hilt)
    kapt(libs.hiltCompiler)

    testApi(libs.bundles.test)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestApi(libs.bundles.androidTest)
}