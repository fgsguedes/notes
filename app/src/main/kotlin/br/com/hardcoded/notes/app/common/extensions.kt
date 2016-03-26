package br.com.hardcoded.notes.app.common

import android.app.Application
import br.com.hardcoded.notes.app.App
import br.com.hardcoded.notes.app.common.injection.component.ApplicationComponent

val Application.applicationComponent: ApplicationComponent
  get() = (this as App).applicationComponent
