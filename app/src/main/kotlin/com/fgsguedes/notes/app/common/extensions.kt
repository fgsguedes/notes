package com.fgsguedes.notes.app.common

import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.fgsguedes.notes.app.App
import com.fgsguedes.notes.app.common.injection.component.ApplicationComponent

val AppCompatActivity.applicationComponent: ApplicationComponent
  get() = (application as App).applicationComponent


val EditText.nullableStringContent: String?
  get() = text.toString().let {
    when {
      it.isNotBlank() -> it.trim()
      else -> null
    }
  }

val <T : Any> T.TAG: String
  get() = javaClass.simpleName

// To be used with view-click in conjunction with single-line `when` clauses
fun AppCompatActivity.execute(f: () -> Unit): Boolean {
  f()
  return true
}
