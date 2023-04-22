import AppDependencies.implementation
import AppDependencies.testImplementation

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.azercell.myazercell.core"
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(ApplicationModules.domainModule))
    implementation(AppDependencies.androidCore)
    implementation(AppDependencies.appCompat)
    implementation(AppDependencies.coroutines)
    implementation(AppDependencies.navigationLibraries)
    implementation(AppDependencies.retrofit2)

    // Lifecycle extensions
    implementation(AppDependencies.lifecycleExtensions)

    testImplementation(AppDependencies.testLibraries)
}