import AppDependencies.testImplementation

plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // Coroutines
    implementation(AppDependencies.kotlinCoroutinesCore)

    // Dagger Hilt
    implementation(AppDependencies.hiltCore)
    kapt(AppDependencies.hiltCompiler)

    implementation(AppDependencies.gson)

    testImplementation(AppDependencies.testLibraries)

    implementation(AppDependencies.libphonenumber)

}