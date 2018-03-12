package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.domain.repository.NoteRepository
import timber.log.Timber
import javax.inject.Inject

class CreateNotePresenter @Inject constructor(
    private val noteRepository: NoteRepository
) {

  private lateinit var view: CreateNoteView

  fun bindView(view: CreateNoteView) {
    this.view = view
  }

  fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {

  }

  fun onSaveInstanceState(outState: Bundle?) {

  }

  fun doneClicked() {
    view.validateForm()
  }

  fun validForm(title: String, content: String?) {
    noteRepository.create(title, content).subscribe(
        { note -> view.noteCreated(note) },
        { error -> Timber.e(error, "Unable to create note") }
    )
  }

  fun invalidForm(): Unit = TODO()
}
