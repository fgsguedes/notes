package com.fgsguedes.notes.app

import android.app.Application
import com.fgsguedes.notes.app.common.injection.component.ApplicationComponent
import com.fgsguedes.notes.app.common.injection.component.DaggerApplicationComponent
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

  val applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder().build()

  @Inject
  lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate() {
    super.onCreate()
    applicationComponent.inject(this)

    Timber.plant(Timber.DebugTree())

    firebaseAuth.signInAnonymously()
        .addOnCompleteListener { authResult ->
          when {
            authResult.isSuccessful -> Timber.i("Auth successful: ${authResult.result.user.uid}")
            else -> Timber.e(authResult.exception, "No auth for you")
          }
        }
  }
}
