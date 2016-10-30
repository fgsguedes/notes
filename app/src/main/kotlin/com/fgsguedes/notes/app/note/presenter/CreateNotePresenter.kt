package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import android.util.Log
import com.fgsguedes.notes.app.common.TAG
import com.fgsguedes.notes.app.common.presenter.Presenter
import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.domain.repository.NoteRepository

interface CreateNotePresenter : Presenter<CreateNoteView> {
  fun doneClicked()

  fun validForm(title: String, content: String?)
  fun invalidForm()
}

class CreateNotePresenterImpl(
    private val noteRepository: NoteRepository
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
    noteRepository.create(title, content).subscribe(
        { note -> view.noteCreated(note) },
        { error -> Log.e(TAG, "Unable to create note", error) }
    )
  }

  override fun invalidForm() = TODO()
}
