import AppDependencies.implementation
import AppDependencies.testImplementation

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        minSdk =  AppConfig.minSdkVersion
        targetSdk =  AppConfig.compileSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String",  "SERVER_URL", "\"http://192.168.100.5/\"")
        }
        debug {
            buildConfigField("String",  "SERVER_URL", "\"http://192.168.100.5/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(ApplicationModules.domainModule))
    // Coroutines
    implementation(AppDependencies.kotlinCoroutinesCore)
    implementation(AppDependencies.coroutines)

    // Dagger Hilt
    implementation(AppDependencies.hiltAndroid)
    kapt(AppDependencies.hiltCompiler)

    //networking
    implementation(AppDependencies.networkLibs)

    //Testing
    testImplementation(AppDependencies.testLibraries)

}