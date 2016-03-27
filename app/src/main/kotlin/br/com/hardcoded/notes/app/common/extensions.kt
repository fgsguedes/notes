package br.com.hardcoded.notes.app.common

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import br.com.hardcoded.notes.app.App
import br.com.hardcoded.notes.app.common.injection.component.ApplicationComponent

val Application.applicationComponent: ApplicationComponent
  get() = (this as App).applicationComponent


val EditText.stringText: String
  get() = text.toString()


// To be used with view-click in conjunction with single-line `when` clauses
fun AppCompatActivity.execute(f: () -> Unit): Boolean {
  f()
  return true
}
