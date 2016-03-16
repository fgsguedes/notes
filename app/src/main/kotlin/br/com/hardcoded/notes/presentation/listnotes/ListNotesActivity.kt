package br.com.hardcoded.notes.presentation.listnotes

import android.os.Bundle
import android.widget.TextView
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.domain.model.Label
import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.presentation.common.activity.BaseActivity

class ListNotesActivity : BaseActivity() {

  val dummyTextView by lazy { findViewById(R.id.dummy_text_view) as TextView }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)

    val note = Note(1, "Dummy note", "Soon this will be big", Label(1, "Hello world"))

    dummyTextView.text = note.toString()
  }
}
