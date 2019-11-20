package io.guedes.notes.app.note.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.guedes.notes.R
import io.guedes.notes.app.common.bind
import io.guedes.notes.app.common.ui.activity.ActivityRequestCodes
import io.guedes.notes.app.note.presenter.ListNotesPresenter
import io.guedes.notes.app.note.ui.adapter.NotesAdapter
import io.guedes.notes.app.note.view.ListNotesView
import io.guedes.notes.domain.model.Note

class ListNotesActivity : AppCompatActivity(), ListNotesView {

    lateinit var presenter: ListNotesPresenter

    private val toolbar: Toolbar by bind(R.id.toolbar)

    private val recyclerNotesList: RecyclerView by bind(R.id.recycler_notes_list)
    private val floatingActionButton: FloatingActionButton by bind(R.id.fab)

    private val adapter = NotesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)

        initUi(savedInstanceState)
        presenter.onCreate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
            KEY_RECYCLER_VIEW_STATE,
            recyclerNotesList.layoutManager?.onSaveInstanceState()
        )
        super.onSaveInstanceState(outState)
    }

    private fun initUi(bundle: Bundle?) {
        setSupportActionBar(toolbar)
        floatingActionButton.setOnClickListener { presenter.onCreateNoteClicked() }

        val state = bundle?.getParcelable<Parcelable>(KEY_RECYCLER_VIEW_STATE)
        recyclerNotesList.layoutManager = LinearLayoutManager(this).apply {
            if (state != null) onRestoreInstanceState(state)
        }
        recyclerNotesList.adapter = adapter
    }

    override fun showNote(note: Note) {
        adapter.addItem(note)
    }

    override fun openCreateNoteForm() {
        val intent = Intent(this, CreateNoteActivity::class.java)
        startActivityForResult(intent, ActivityRequestCodes.CREATE_NOTE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val note = data?.getParcelableExtra<Note>(CreateNoteActivity.EXTRA_NOTE)

        if (resultCode != RESULT_OK || note == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }

        when (requestCode) {
            ActivityRequestCodes.CREATE_NOTE -> presenter.noteCreated(note)
        }
    }

    companion object {
        const val KEY_RECYCLER_VIEW_STATE = "KEY_RECYCLER_VIEW_STATE"
    }
}
