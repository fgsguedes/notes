package br.com.hardcoded.notes.app.note.presenter

import android.os.Bundle
import android.util.Log
import br.com.hardcoded.notes.app.note.view.ListNotesView
import br.com.hardcoded.notes.domain.usecase.GetNoteListUseCase
import rx.observers.Subscribers

class ListNotesPresenterImpl(
    private val getNoteListUseCase: GetNoteListUseCase
) : ListNotesPresenter {

  private lateinit var view: ListNotesView

  override fun bindView(view: ListNotesView) {
    this.view = view
  }

  override fun onCreateNoteClicked() {
    view.openCreateNoteForm()
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    getNoteListUseCase.subscribe(Subscribers.create(
        { notes -> view.showNotes(notes) },
        { error -> Log.e(TAG, "Unable do get notes", error) }
    ))
  }

  override fun onSaveInstanceState(outState: Bundle?) {
  }

  companion object {
    val TAG = ListNotesPresenterImpl::class.java.simpleName
  }
}
