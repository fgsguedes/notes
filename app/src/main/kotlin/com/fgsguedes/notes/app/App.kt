package com.fgsguedes.notes.app

import android.app.Application
import android.util.Log
import com.fgsguedes.notes.app.common.TAG
import com.fgsguedes.notes.app.common.injection.component.ApplicationComponent
import com.fgsguedes.notes.app.common.injection.component.DaggerApplicationComponent
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class App : Application() {

  val applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder().build()

  @Inject
  lateinit var firebaseAuth: FirebaseAuth

  override fun onCreate() {
    super.onCreate()
    applicationComponent.inject(this)

    firebaseAuth.signInAnonymously()
        .addOnCompleteListener { authResult ->
          when {
            authResult.isSuccessful -> Log.e(TAG, "Auth successful: ${authResult.result.user.uid}")
            else -> Log.e(TAG, "No auth for you")
          }
        }
  }
}
