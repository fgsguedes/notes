package com.fgsguedes.notes.app.note.presenter

import android.os.Bundle
import android.util.Log
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.usecase.GetNoteListUseCase
import rx.observers.Subscribers

class ListNotesPresenterImpl(
    private val getNoteListUseCase: GetNoteListUseCase
) : ListNotesPresenter {

  private lateinit var view: ListNotesView

  override fun bindView(view: ListNotesView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    getNoteListUseCase.subscribe(Subscribers.create(
        { note -> view.showNote(note) },
        { error -> Log.e(TAG, "Unable do get notes", error) }
    ))
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
