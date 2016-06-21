package com.fgsguedes.notes.app

import android.app.Application
import com.fgsguedes.notes.app.common.injection.component.DaggerApplicationComponent

class App : Application() {

  val applicationComponent by lazy {
    DaggerApplicationComponent.create()
  }
}
