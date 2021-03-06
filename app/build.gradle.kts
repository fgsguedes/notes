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
        applicationId = "io.guedes.notes.app"

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

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin(Deps.kotlinStdlib))

    implementation(project(":domain"))
    implementation(project(":architecture"))

    implementation(Deps.coroutines)

    kapt(Deps.roomCompiler)

    implementation(Deps.androidxCore)

    implementation(Deps.appCompat)
    implementation(Deps.activityKtx)
    implementation(Deps.recyclerView)
    implementation(Deps.constraintLayout)
    implementation(Deps.coordinatorLayout)

    implementation(Deps.lifecycleRuntime)
    implementation(Deps.lifecycleLivedata)
    implementation(Deps.lifecycleViewModel)

    implementation(Deps.googleMaterial)

    implementation(Deps.roomKtx)
    implementation(Deps.roomRuntime)

    implementation(Deps.timber)

    testImplementation(Deps.junit)
    testImplementation(Deps.assertJ)
    testImplementation(Deps.mockito)
    testImplementation(Deps.mockitoInline)
    testImplementation(Deps.mockitoKotlin)
    testImplementation(Deps.coroutinesTest)

    androidTestImplementation(Deps.androidxTestRunner)
    androidTestImplementation(Deps.androidxEspressoCore)
}
