package com.fgsguedes.notes.app.common

import android.support.v7.app.AppCompatActivity
import com.fgsguedes.notes.app.App
import com.fgsguedes.notes.app.common.injection.component.ApplicationComponent

val AppCompatActivity.applicationComponent: ApplicationComponent
  get() = (application as App).applicationComponent

// To be used with view-click in conjunction with single-line `when` clauses
fun returningTrue(f: () -> Unit): Boolean {
  f()
  return true
}
