package com.fgsguedes.notes.app

import android.app.Activity
import android.app.Application
import com.fgsguedes.notes.app.common.injection.component.DaggerApplicationComponent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .injectMembers(this)

        Timber.plant(Timber.DebugTree())

        firebaseDatabase.setPersistenceEnabled(true)
        firebaseAuth.signInAnonymously()
            .addOnCompleteListener { authResult ->
                when {
                    authResult.isSuccessful -> Timber.i("Auth successful: ${authResult.result.user.uid}")
                    else -> Timber.e(authResult.exception, "No auth for you")
                }
            }
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
