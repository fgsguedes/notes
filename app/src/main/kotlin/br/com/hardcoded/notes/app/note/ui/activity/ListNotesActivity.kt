package br.com.hardcoded.notes.app.note.ui.activity

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.ui.activity.BaseActivity
import br.com.hardcoded.notes.app.note.injection.component.DaggerNotesComponent
import br.com.hardcoded.notes.app.note.presenter.ListNotesPresenter
import br.com.hardcoded.notes.app.note.ui.adapter.NotesAdapter
import br.com.hardcoded.notes.app.note.view.ListNotesView
import br.com.hardcoded.notes.app.common.applicationComponent
import br.com.hardcoded.notes.domain.model.Note
import javax.inject.Inject

class ListNotesActivity : BaseActivity(), ListNotesView {

  val component by lazy {
    DaggerNotesComponent.builder()
        .applicationComponent(application.applicationComponent)
        .build()
  }

  @Inject lateinit var presenter: ListNotesPresenter

  val recyclerNotesList by lazy { findViewById(R.id.recycler_notes_list) as RecyclerView }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)

    initInjector()
    initUi(savedInstanceState)
    initActivity(savedInstanceState)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putParcelable(KEY_RECYCLER_VIEW_STATE, recyclerNotesList.layoutManager.onSaveInstanceState())
    super.onSaveInstanceState(outState)
  }

  private fun initInjector() {
    component.inject(this)
  }

  private fun initUi(savedInstanceState: Bundle?) {
    val state = savedInstanceState?.getParcelable<Parcelable>(KEY_RECYCLER_VIEW_STATE)

    recyclerNotesList.layoutManager = LinearLayoutManager(this).apply {
      if (state != null) onRestoreInstanceState(state)
    }
  }

  private fun initActivity(savedInstanceState: Bundle?) {
    presenter.bindView(this)
    presenter.onCreate(savedInstanceState, intent.extras)
  }

  override fun showNotes(notes: List<Note>) {
    recyclerNotesList.adapter = NotesAdapter(this, notes)
  }

  companion object {
    val KEY_RECYCLER_VIEW_STATE = "recyclerViewState"
  }
}
