package br.com.hardcoded.notes

import android.app.Application
import br.com.hardcoded.notes.app.common.injection.component.ApplicationComponent
import br.com.hardcoded.notes.app.common.injection.component.DaggerApplicationComponent
import br.com.hardcoded.notes.app.common.injection.module.AndroidModule

class App : Application() {

  val applicationComponent by lazy {
    DaggerApplicationComponent.builder()
        .androidModule(AndroidModule(this))
        .build()
  }
}

val Application.applicationComponent: ApplicationComponent
  get() = (this as App).applicationComponent
