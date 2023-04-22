import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object AppDependencies {

    const val appCompat = "androidx.appcompat:appcompat:${ApplicationVersions.appCompat}"
    const val androidCore = "androidx.core:core-ktx:${ApplicationVersions.appCore}"
    const val material = "com.google.android.material:material:${ApplicationVersions.materialVersion}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${ApplicationVersions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${ApplicationVersions.navigationVersion}"

    //Kotlin lib
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${ApplicationVersions.coroutinesVersion}"
    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${ApplicationVersions.kotlinCoroutinesCore}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${ApplicationVersions.coroutinesVersion}"

    // Lifecycle extensions
    private const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    private const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${ApplicationVersions.lifecycle}"
    private const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${ApplicationVersions.lifecycle}"
    private const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${ApplicationVersions.lifecycle}"

    //Dagger Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${ApplicationVersions.hiltVersion}"
    const val hiltCore = "com.google.dagger:hilt-core:${ApplicationVersions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${ApplicationVersions.hiltVersion}"

    //Net client
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${ApplicationVersions.okhttpVersion}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${ApplicationVersions.okhttpVersion}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${ApplicationVersions.retrofitVersion}"
    const val gson = "com.google.code.gson:gson:${ApplicationVersions.retrofitVersion}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${ApplicationVersions.retrofitVersion}"

    //Test components
    const val jUnit = "junit:junit:${ApplicationVersions.junitVersion}"
    const val mockk = "io.mockk:mockk:${ApplicationVersions.mockkVersion}"
    private const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${ApplicationVersions.kotlinCoroutinesCore}"
    private const val coreTesting = "androidx.arch.core:core-testing:2.1.0"

    //Security
    const val encryptedSharedPref: String = "androidx.security:security-crypto:1.1.0-alpha03"

    //Coil
    const val coil = "io.coil-kt:coil:${ApplicationVersions.coilVersion}"

    val appLibraries = arrayListOf<String>().apply {
        add(appCompat)
        add(androidCore)
        add(material)
    }

    val navigationLibraries = arrayListOf<String>().apply {
        add(navigationFragment)
        add(navigationUi)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(jUnit)
        add(mockk)
        add(coroutinesTest)
        add(coreTesting)
    }

    val lifecycleExtensions = arrayListOf<String>().apply {
        add(lifecycleExtension)
        add(lifecycleCommon)
        add(lifecycleRuntime)
        add(lifecycleViewmodel)
    }

    val networkLibs = arrayListOf<String>().apply {
        add(okhttp3)
        add(loggingInterceptor)
        add(retrofit2)
        add(gson)
        add(gsonConverter)
    }

    //util functions for adding the different type dependencies from build.gradle file
    fun DependencyHandler.kapt(list: List<String>) {
        list.forEach { dependency ->
            add("kapt", dependency)
        }
    }

    fun DependencyHandler.kapt(libPath: String) {
        add("kapt", libPath)
    }

    fun DependencyHandler.implementation(list: List<String>) {
        list.forEach { dependency ->
            add("implementation", dependency)
        }
    }

    fun DependencyHandler.implementation(lib: String) {
        add("implementation", lib)
    }

    private fun DependencyHandler.implementation(project: ProjectDependency) {
        add("implementation", project)
    }

    fun DependencyHandler.androidTestImplementation(list: List<String>) {
        list.forEach { dependency ->
            add("androidTestImplementation", dependency)
        }
    }

    fun DependencyHandler.testImplementation(list: List<String>) {
        list.forEach { dependency ->
            add("testImplementation", dependency)
        }
    }

    fun DependencyHandler.featureDependencies() {
        implementation(project(ApplicationModules.coreModule))
        implementation(project(ApplicationModules.domainModule))
        implementation(project(ApplicationModules.uiToolkit))
        implementation(appLibraries)
        implementation(testLibraries)
        implementation(navigationLibraries)
        implementation(hiltAndroid)
        kapt(hiltCompiler)
        implementation(coil)
    }

}