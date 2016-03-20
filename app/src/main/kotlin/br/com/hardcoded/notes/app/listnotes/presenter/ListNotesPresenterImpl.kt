package br.com.hardcoded.notes.app.listnotes.presenter

import android.os.Bundle
import android.util.Log
import br.com.hardcoded.notes.app.listnotes.view.ListNotesView
import br.com.hardcoded.notes.domain.usecase.GetNoteListUseCase
import rx.observers.Subscribers

class ListNotesPresenterImpl(
    private val getNoteListUseCase: GetNoteListUseCase
) : ListNotesPresenter {

  lateinit var view: ListNotesView

  override fun bindView(view: ListNotesView) {
    this.view = view
  }

  override fun onCreate(savedState: Bundle?, intentExtras: Bundle?) {
    getNoteListUseCase.subscribe(Subscribers.create(
        { it.firstOrNull()?.apply { view.showNote(this) } },
        { Log.e(TAG, "Unable do get notes", it) }
    ))
  }

  override fun onSaveInstanceState(bundle: Bundle?) {
  }

  companion object {
    val TAG = ListNotesPresenterImpl::class.java.simpleName
  }
}
