package br.com.hardcoded.notes.app.listnotes.ui.activity

import android.os.Bundle
import android.widget.TextView
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.ui.activity.BaseActivity
import br.com.hardcoded.notes.app.listnotes.presenter.ListNotesPresenterImpl
import br.com.hardcoded.notes.app.listnotes.view.ListNotesView
import br.com.hardcoded.notes.domain.model.Note

class ListNotesActivity : BaseActivity(), ListNotesView {
  val dummyTextView by lazy { findViewById(R.id.dummy_text_view) as TextView }

  val presenter = ListNotesPresenterImpl()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)

    presenter.bindView(this)
    presenter.onCreate(savedInstanceState, intent.extras)
  }

  override fun showNote(note: Note) {
    dummyTextView.text = note.toString()
  }
}
