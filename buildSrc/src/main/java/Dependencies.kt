object Version {
    const val kotlin = "1.3.61"

    const val gradle = "6.0.1"

    const val versionCode = 1
    const val versionName = "0.0.1"

    const val compileSdk = 29
    const val buildTools = "29.0.2"

    const val minSdk = 21
    const val targetSdk = 29
}

object Deps {
    private const val roomVersion = "2.2.2"
    private const val coroutinesVersion = "1.3.3"
    private const val lifecycleVersion = "2.2.0-rc02"

    const val androidGradlePlugin = "com.android.tools.build:gradle:3.5.3"
    const val kotlinGradlePlugin = "gradle-plugin"
    const val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:1.0.0"

    const val kotlinStdlib = "stdlib-jdk8"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    const val androidxCore = "androidx.core:core-ktx:1.1.0"

    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val activityKtx = "androidx.activity:activity-ktx:1.0.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.0.0"

    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

    const val googleMaterial = "com.google.android.material:material:1.0.0"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"

    const val junit = "org.junit.jupiter:junit-jupiter:5.5.2"
    const val assertJ = "org.assertj:assertj-core:3.14.0"
    const val mockito = "org.mockito:mockito-core:3.2.4"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"

    const val androidxTestRunner = "androidx.test:runner:1.2.0"
    const val androidxEspressoCore = "androidx.test.espresso:espresso-core:3.2.0"
}
