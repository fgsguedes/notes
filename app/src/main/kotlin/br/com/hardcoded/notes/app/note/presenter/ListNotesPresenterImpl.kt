package br.com.hardcoded.notes.app.note.presenter

import android.os.Bundle
import android.util.Log
import br.com.hardcoded.notes.app.note.view.ListNotesView
import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.usecase.CreateNoteUseCase
import br.com.hardcoded.notes.domain.usecase.GetNoteListUseCase
import rx.observers.Subscribers

class ListNotesPresenterImpl(
    private val getNoteListUseCase: GetNoteListUseCase,
    private val createNoteUseCase: CreateNoteUseCase
) : ListNotesPresenter {

  lateinit var view: ListNotesView

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

  override fun onSaveInstanceState(bundle: Bundle?) {
  }

  override fun create(note: Note) {
    createNoteUseCase.subscribe(note, Subscribers.create(
        {}, // Do nothing on `onNext`
        { error -> Log.e(TAG, "Unable to create note", error) },
        { Log.i(TAG, "Note created") }
    ))
  }

  companion object {
    val TAG = ListNotesPresenterImpl::class.java.simpleName
  }
}
