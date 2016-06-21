package com.fgsguedes.notes.app

import android.app.Application
import com.fgsguedes.notes.app.common.injection.component.DaggerApplicationComponent
import com.fgsguedes.notes.app.common.injection.module.AndroidModule

class App : Application() {

  val applicationComponent by lazy {
    DaggerApplicationComponent.builder()
        .androidModule(AndroidModule(this))
        .build()
  }
}
