package br.com.hardcoded.notes.app.note.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.applicationComponent
import br.com.hardcoded.notes.app.common.execute
import br.com.hardcoded.notes.app.common.ui.activity.BaseActivity
import br.com.hardcoded.notes.app.note.injection.component.DaggerNotesComponent
import br.com.hardcoded.notes.app.note.presenter.CreateNotePresenter
import br.com.hardcoded.notes.app.note.view.CreateNoteView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import javax.inject.Inject

class CreateNoteActivity : BaseActivity(), CreateNoteView, AnkoLogger {

  val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

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

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_activity_create_note, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_item_done_creating_note -> execute {
        debug("Done creating notes")
      }
      else -> false
    }
  }
}
