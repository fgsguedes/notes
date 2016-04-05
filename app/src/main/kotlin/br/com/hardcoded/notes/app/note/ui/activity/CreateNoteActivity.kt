package br.com.hardcoded.notes.app.note.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.execute
import br.com.hardcoded.notes.app.common.ui.activity.BaseActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

class CreateNoteActivity : BaseActivity(), AnkoLogger {

  val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_note)

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
