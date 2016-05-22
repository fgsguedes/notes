package br.com.hardcoded.notes.app.common

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import br.com.hardcoded.notes.app.App
import br.com.hardcoded.notes.app.common.injection.component.ApplicationComponent

val Application.applicationComponent: ApplicationComponent
  get() = (this as App).applicationComponent


// To be used with view-click in conjunction with single-line `when` clauses
fun AppCompatActivity.returningTrue(f: () -> Unit): Boolean {
  f()
  return true
}
