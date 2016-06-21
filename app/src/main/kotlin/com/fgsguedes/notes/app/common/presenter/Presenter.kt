package com.fgsguedes.notes.app.common.presenter

import android.os.Bundle

interface Presenter<T> {
  fun onCreate(savedState: Bundle?, intentExtras: Bundle?)
  fun onSaveInstanceState(outState: Bundle?)
  fun bindView(view: T)
}
