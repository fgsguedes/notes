package io.guedes.notes.app.note.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import io.guedes.notes.R
import io.guedes.notes.app.common.bind
import io.guedes.notes.app.common.returningTrue
import io.guedes.notes.app.note.presenter.CreateNotePresenter
import io.guedes.notes.app.note.view.CreateNoteView
import io.guedes.notes.domain.model.Note

class CreateNoteActivity : AppCompatActivity(), CreateNoteView {

    lateinit var presenter: CreateNotePresenter

    private val rootView: View by bind(R.id.create_note_root_view)
    private val toolbar: Toolbar by bind(R.id.toolbar)

    private val editTextNoteTitle: EditText by bind(R.id.edit_text_note_title)
    private val editTextNoteContent: EditText by bind(R.id.edit_text_note_content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu) = returningTrue {
        menuInflater.inflate(R.menu.menu_activity_create_note, menu)
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
        val intent = Intent().putExtra(EXTRA_NOTE, note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun invalidForm() {
        Snackbar.make(
            rootView,
            R.string.create_note_fields_required_message,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.create_note_fields_required_action) {}
            .show()
    }

    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
    }
}
