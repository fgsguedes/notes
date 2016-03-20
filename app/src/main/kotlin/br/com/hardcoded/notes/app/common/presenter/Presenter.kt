package br.com.hardcoded.notes.app.common.presenter

import android.os.Bundle

interface Presenter<T> {
  fun onCreate(savedState: Bundle?, intentExtras: Bundle?)
  fun onSaveInstanceState(bundle: Bundle?)
  fun bindView(view: T)
}