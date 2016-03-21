package br.com.hardcoded.notes

import android.app.Application
import br.com.hardcoded.notes.app.injection.component.ApplicationComponent
import br.com.hardcoded.notes.app.injection.component.DaggerApplicationComponent
import br.com.hardcoded.notes.app.injection.module.AndroidModule

class App : Application() {

  val applicationComponent by lazy {
    DaggerApplicationComponent.builder()
        .androidModule(AndroidModule(this))
        .build()
  }
}

val Application.applicationComponent: ApplicationComponent
  get() = (this as App).applicationComponent
