object Deps {
    const val kotlinStdlib = "stdlib-jdk7"

    private const val moshiVersion = "1.8.0"
    const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    const val moshiKotlinCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.13"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

    const val okio = "com.squareup.okio:okio:2.4.1"

    const val okHttp = "com.squareup.okhttp3:okhttp:4.2.2"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.2.2"

    const val retrofit = "com.squareup.retrofit2:retrofit:2.6.2"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:2.6.2"
    const val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:2.6.2"

    const val androidxCore = "androidx.core:core-ktx:1.1.0"
    const val androidxAppCompat = "androidx.appcompat:appcompat:1.1.0"
    const val androidxRecyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val androidxCoordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.0.0"

    const val googleMaterial = "com.google.android.material:material:1.0.0"

    private const val roomVersion = "2.2.1"
    const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    const val roomRxJava = "androidx.room:room-rxjava2:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"

    const val junit = "junit:junit:4.12"

    const val androidxTestRunner = "androidx.test:runner:1.2.0"
    const val androidxEspressoCore = "androidx.test.espresso:espresso-core:3.2.0"
}
