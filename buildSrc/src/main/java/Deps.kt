object Deps {
    const val kotlinStdlib = "stdlib-jdk7"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"

    const val androidxCore = "androidx.core:core-ktx:1.1.0"

    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val activityKtx = "androidx.activity:activity-ktx:1.0.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.0.0"

    private const val lifecycleVersion = "2.2.0-rc02"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

    const val googleMaterial = "com.google.android.material:material:1.0.0"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    private const val roomVersion = "2.2.2"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"

    const val junit = "junit:junit:4.12"

    const val androidxTestRunner = "androidx.test:runner:1.2.0"
    const val androidxEspressoCore = "androidx.test.espresso:espresso-core:3.2.0"
}
