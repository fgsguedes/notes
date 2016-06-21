package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import android.util.Log
import com.fgsguedes.notes.app.common.TAG
import com.fgsguedes.notes.app.common.presenter.Presenter
import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.domain.usecase.CreateNoteUseCase
import rx.observers.Subscribers

interface CreateNotePresenter : Presenter<CreateNoteView> {
  fun doneClicked()

  fun validForm(title: String, content: String?)
  fun invalidForm()
}

class CreateNotePresenterImpl(
    private val createNoteUseCase: CreateNoteUseCase
) : CreateNotePresenter {

  private lateinit var view: CreateNoteView

  override fun bindView(view: CreateNoteView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {

  }

  override fun onSaveInstanceState(outState: Bundle?) {

  }

  override fun doneClicked() {
    view.validateForm()
  }

  override fun validForm(title: String, content: String?) {
    createNoteUseCase.subscribe(title, content, Subscribers.create(
        { note -> view.noteCreated(note) },
        { error -> Log.e(TAG, "Unable to create note", error) },
        { Log.i(TAG, "Note created!") }
    ))
  }

  override fun invalidForm() = TODO()
}
