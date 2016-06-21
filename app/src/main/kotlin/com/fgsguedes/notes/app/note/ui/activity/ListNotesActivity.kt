package com.fgsguedes.notes.app.note.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.fgsguedes.notes.R
import com.fgsguedes.notes.app.common.applicationComponent
import com.fgsguedes.notes.app.common.ui.activity.BaseActivity
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenter
import com.fgsguedes.notes.app.note.ui.adapter.NotesAdapter
import com.fgsguedes.notes.app.note.view.ListNotesView
import com.fgsguedes.notes.domain.model.Note
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class ListNotesActivity : BaseActivity(), ListNotesView {

  @Inject lateinit var presenter: ListNotesPresenter

  val recyclerNotesList by lazy { findViewById(R.id.recycler_notes_list) as RecyclerView }
  val floatingActionButton by lazy { findViewById(R.id.fab) as FloatingActionButton }
  val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

  val adapter = NotesAdapter(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)

    applicationComponent.inject(this)
    initUi(savedInstanceState)
    initActivity(savedInstanceState)
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

  private fun initActivity(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState, intent.extras)
  }

  override fun showNote(note: Note) {
    adapter.addItem(note)
  }

  override fun openCreateNoteForm() = startActivityForResult(intentFor<CreateNoteActivity>(), 1)

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    when (requestCode) {
      1 -> data?.getParcelableExtra<Note>("note")?.let { note ->
        adapter.addItem(note)
      }
    }
  }

  companion object {
    val KEY_RECYCLER_VIEW_STATE = "recyclerViewState"
  }
}
