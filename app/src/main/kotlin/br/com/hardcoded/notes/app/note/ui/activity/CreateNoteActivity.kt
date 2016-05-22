package br.com.hardcoded.notes.app.note.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.applicationComponent
import br.com.hardcoded.notes.app.common.returningTrue
import br.com.hardcoded.notes.app.common.ui.activity.BaseActivity
import br.com.hardcoded.notes.app.note.injection.component.DaggerNotesComponent
import br.com.hardcoded.notes.app.note.presenter.CreateNotePresenter
import br.com.hardcoded.notes.app.note.view.CreateNoteView
import br.com.hardcoded.notes.domain.model.Note
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

class CreateNoteActivity : BaseActivity(), CreateNoteView, AnkoLogger {

  val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

  val editTextNoteTitle by lazy { findViewById(R.id.edit_text_note_title) as EditText }
  val editTextNoteContent by lazy { findViewById(R.id.edit_text_note_content) as EditText }

  @Inject
  lateinit var presenter: CreateNotePresenter

  val component by lazy {
    DaggerNotesComponent.builder()
        .applicationComponent(application.applicationComponent)
        .build()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_note)

    component.inject(this)
    initUi(savedInstanceState)
    initPresenter(savedInstanceState)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    presenter.onSaveInstanceState(outState)
  }

  private fun initUi(savedInstanceState: Bundle?) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun initPresenter(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState, intent.extras)
  }

  override fun onCreateOptionsMenu(menu: Menu) = returningTrue {
    menuInflater.inflate(R.menu.menu_activity_create_note, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_item_done_creating_note -> returningTrue {
        presenter.doneClicked()
      }
      else -> false
    }
  }

  override fun validateForm() {
    val title = editTextNoteTitle.text.toString()
    val content = editTextNoteContent.text.toString()

    when {
      title.isNotBlank() && content.isNotBlank() -> presenter.validForm(title, content)
      else -> presenter.invalidForm()
    }
  }

  override fun noteCreated(note: Note) {
    val intent = Intent()
        .putExtra("note", note)

    setResult(RESULT_OK, intent)
    finish()
  }
}
