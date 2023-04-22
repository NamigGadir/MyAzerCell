import AppDependencies.hiltAndroid
import AppDependencies.hiltCompiler

plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(project(ApplicationModules.coreModule))
    implementation(project(ApplicationModules.domainModule))
    implementation(project(ApplicationModules.dataModule))
    implementation(project(ApplicationModules.homeModule))
    implementation(project(ApplicationModules.authModule))

    implementation(AppDependencies.androidCore)
    implementation(AppDependencies.appCompat)
    implementation(AppDependencies.material)
    testImplementation(AppDependencies.jUnit)
    implementation(AppDependencies.navigationFragment)
    implementation(AppDependencies.navigationUi)
    implementation(AppDependencies.coroutines)
    implementation(hiltAndroid)
    kapt(hiltCompiler)
}
