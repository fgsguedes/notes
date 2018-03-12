package com.fgsguedes.notes.app.note.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.fgsguedes.notes.R
import com.fgsguedes.notes.app.common.ui.activity.ActivityRequestCodes
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenter
import com.fgsguedes.notes.app.note.ui.adapter.NotesAdapter
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import dagger.android.AndroidInjection
import javax.inject.Inject

class ListNotesActivity : AppCompatActivity(), ListNotesView {

  @Inject
  lateinit var presenter: ListNotesPresenter

  private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

  private val recyclerNotesList by lazy { findViewById<RecyclerView>(R.id.recycler_notes_list) }
  private val floatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.fab) }

  private val adapter = NotesAdapter(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)

    AndroidInjection.inject(this)

    initUi(savedInstanceState)
    presenter.onCreate()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putParcelable(KEY_RECYCLER_VIEW_STATE, recyclerNotesList.layoutManager.onSaveInstanceState())
    super.onSaveInstanceState(outState)
  }

  private fun initUi(savedInstanceState: Bundle?) {
    setSupportActionBar(toolbar)
    floatingActionButton.setOnClickListener { presenter.onCreateNoteClicked() }

    val state = savedInstanceState?.getParcelable<Parcelable>(KEY_RECYCLER_VIEW_STATE)
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

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    val note = data?.getParcelableExtra<Note>("note")

    if (resultCode != RESULT_OK || note == null) {
      super.onActivityResult(requestCode, resultCode, data)
      return
    }

    when (requestCode) {
      ActivityRequestCodes.CREATE_NOTE -> presenter.noteCreated(note)
    }
  }

  companion object {
    const val KEY_RECYCLER_VIEW_STATE = "recyclerViewState"
  }
}
