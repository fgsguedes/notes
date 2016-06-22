package com.fgsguedes.notes.app.note.presenter

import com.fgsguedes.notes.app.common.presenter.Presenter
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note

interface ListNotesPresenter : Presenter<ListNotesView> {
  fun onCreateNoteClicked(): Unit
  fun noteCreated(note: Note)
}
