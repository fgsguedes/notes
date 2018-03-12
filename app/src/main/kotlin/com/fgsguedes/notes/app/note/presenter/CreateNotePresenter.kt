package com.fgsguedes.notes.app.note.presenter

import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.domain.repository.NoteRepository
import timber.log.Timber
import javax.inject.Inject

class CreateNotePresenter @Inject constructor(
    private val view: CreateNoteView,
    private val noteRepository: NoteRepository
) {

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
