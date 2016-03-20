package br.com.hardcoded.notes.app.listnotes.ui.activity

import android.os.Bundle
import android.widget.TextView
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.ui.activity.BaseActivity
import br.com.hardcoded.notes.app.injection.component.DaggerListNotesComponent
import br.com.hardcoded.notes.app.listnotes.presenter.ListNotesPresenter
import br.com.hardcoded.notes.app.listnotes.view.ListNotesView
import br.com.hardcoded.notes.domain.model.Note
import javax.inject.Inject

class ListNotesActivity : BaseActivity(), ListNotesView {

  val component by lazy { DaggerListNotesComponent.builder().build() }

  @Inject lateinit var presenter: ListNotesPresenter

  val dummyTextView by lazy { findViewById(R.id.dummy_text_view) as TextView }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)

    initInjector()
    initActivity(savedInstanceState)
  }

  private fun initInjector() {
    component.inject(this)
  }

  private fun initActivity(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState, intent.extras)
  }

  override fun showNote(note: Note) {
    dummyTextView.text = note.toString()
  }
}
