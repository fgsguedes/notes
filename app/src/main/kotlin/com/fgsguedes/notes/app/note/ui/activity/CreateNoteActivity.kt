package com.fgsguedes.notes.app.note.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.fgsguedes.notes.R
import com.fgsguedes.notes.app.common.returningTrue
import com.fgsguedes.notes.app.note.presenter.CreateNotePresenter
import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.domain.model.Note
import dagger.android.AndroidInjection
import javax.inject.Inject

class CreateNoteActivity : AppCompatActivity(),
    CreateNoteView {

    @Inject
    lateinit var presenter: CreateNotePresenter

    private val rootView by lazy { findViewById<View>(R.id.create_note_root_view) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val editTextNoteTitle by lazy {
        findViewById<EditText>(
            R.id.edit_text_note_title
        )
    }
    private val editTextNoteContent by lazy {
        findViewById<EditText>(
            R.id.edit_text_note_content
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        AndroidInjection.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu) =
        returningTrue {
            menuInflater.inflate(
                R.menu.menu_activity_create_note,
                menu
            )
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_done_creating_note -> returningTrue {
                presenter.doneClicked(
                    editTextNoteTitle.text.toString(),
                    editTextNoteContent.text.toString()
                )
            }
            else -> false
        }
    }

    override fun noteCreated(note: Note) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra("note", note)
        )
        finish()
    }

    override fun invalidForm() {
        Snackbar.make(
            rootView,
            R.string.create_note_fields_required_message,
            Snackbar.LENGTH_LONG
        )
            .setAction(
                R.string.create_note_fields_required_action,
                {})
            .show()
    }
}
