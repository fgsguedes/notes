package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import android.util.Log
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.repository.NoteRepository

class ListNotesPresenterImpl(
    private val notesRepository: NoteRepository
) : ListNotesPresenter {

  private lateinit var view: ListNotesView

  override fun bindView(view: ListNotesView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    notesRepository.list()
        .subscribe(
            { note -> view.showNote(note) },
            { error -> Log.e(TAG, "Unable do get notes", error) }
        )
  }

  override fun onSaveInstanceState(outState: Bundle?) {
  }

  override fun onCreateNoteClicked() {
    view.openCreateNoteForm()
  }

  override fun noteCreated(note: Note) {
    view.showNote(note)
  }

  companion object {
    val TAG = ListNotesPresenterImpl::class.java.simpleName
  }
}
