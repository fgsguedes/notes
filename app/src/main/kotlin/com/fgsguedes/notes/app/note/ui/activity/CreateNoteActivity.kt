package com.fgsguedes.notes.app.note.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.fgsguedes.notes.R
import com.fgsguedes.notes.app.common.returningTrue
import com.fgsguedes.notes.app.note.presenter.CreateNotePresenter
import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.domain.model.Note
import dagger.android.AndroidInjection
import javax.inject.Inject

class CreateNoteActivity : AppCompatActivity(), CreateNoteView {

  @Inject
  lateinit var presenter: CreateNotePresenter

  private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

  private val editTextNoteTitle by lazy { findViewById<EditText>(R.id.edit_text_note_title) }
  private val editTextNoteContent by lazy { findViewById<EditText>(R.id.edit_text_note_content) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_note)

    AndroidInjection.inject(this)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
    setResult(Activity.RESULT_OK, Intent().putExtra("note", note))
    finish()
  }
}
