import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("com.getkeepsafe.dexcount")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Version.compileSdk)
    buildToolsVersion(Version.buildTools)

    defaultConfig {
        applicationId = "io.guedes.notes"

        minSdkVersion(Version.minSdk)
        targetSdkVersion(Version.targetSdk)

        versionCode = Version.versionCode
        versionName = Version.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(kotlin(Deps.kotlinStdlib, KotlinCompilerVersion.VERSION))

    implementation(Deps.androidxCore)
    implementation(Deps.androidxAppCompat)
    implementation(Deps.androidxRecyclerView)
    implementation(Deps.androidxConstraintLayout)
    implementation(Deps.androidxCoordinatorLayout)

    implementation(Deps.googleMaterial)

    implementation(Deps.roomRuntime)

    testImplementation(Deps.junit)

    androidTestImplementation(Deps.androidxTestRunner)
    androidTestImplementation(Deps.androidxEspressoCore)
}
