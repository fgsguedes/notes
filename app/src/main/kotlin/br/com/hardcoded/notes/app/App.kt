package br.com.hardcoded.notes.app

import android.app.Application
import br.com.hardcoded.notes.app.common.injection.component.DaggerApplicationComponent
import br.com.hardcoded.notes.app.common.injection.module.AndroidModule

class App : Application() {

  val applicationComponent by lazy {
    DaggerApplicationComponent.builder()
        .androidModule(AndroidModule(this))
        .build()
  }
}
