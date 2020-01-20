plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Version.compileSdk)
    buildToolsVersion(Version.buildTools)

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

    kapt(Deps.roomCompiler)

    implementation(Deps.coroutines)
    implementation(Deps.lifecycleViewModel)

    implementation(Deps.roomKtx)
    implementation(Deps.roomRuntime)
}
