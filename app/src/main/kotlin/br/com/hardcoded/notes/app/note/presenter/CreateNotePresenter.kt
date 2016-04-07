package br.com.hardcoded.notes.app.note.presenter

import android.os.Bundle
import br.com.hardcoded.notes.app.common.presenter.Presenter
import br.com.hardcoded.notes.app.note.view.CreateNoteView
import br.com.hardcoded.notes.domain.usecase.CreateNoteUseCase
import org.jetbrains.anko.AnkoLogger
import rx.observers.Subscribers

interface CreateNotePresenter : Presenter<CreateNoteView>, AnkoLogger {
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
        { note -> error(note) },
        { error -> },
        { error("Note created!") }
    ))
  }

  override fun invalidForm() = TODO()
}
