package justmakeitwork

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.returningTrue

class CreateNoteActivity : AppCompatActivity() {

  //region Fields
  private val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

  private val editTextTitle by lazy { findViewById(R.id.edit_text_note_title) as EditText }
  private val editTextContent by lazy { findViewById(R.id.edit_text_note_content) as EditText }

  private val databaseHelper by lazy { Database(this) }
  //endregion

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_note)
    setSupportActionBar(toolbar)
  }

  //region Android Overrides
  override fun onCreateOptionsMenu(menu: Menu) = returningTrue {
    menuInflater.inflate(R.menu.menu_activity_create_note, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_item_done_creating_note -> ifFormIsValid { title, content ->
        saveNote(title, content)
      }
      else -> false
    }
  }
  //endregion

  private fun ifFormIsValid(f: (title: String, content: String) -> Unit): Boolean {

    val title = editTextTitle.text.toString()
    val content = editTextContent.text.toString()

    if (title.isNotBlank() && content.isNotBlank()) f(title, content)

    return true
  }

  private fun saveNote(title: String, content: String) {
    val database = databaseHelper.writableDatabase
    val transaction = database.beginTransaction()

    val contentValues = ContentValues(2).apply {
      put("title", title)
      put("content", content)
    }

    val id = database.insert("Note", null, contentValues)
    database.setTransactionSuccessful()
    database.close()

    Toast.makeText(this, "Note created with ID: $id", Toast.LENGTH_SHORT).show()
    finish()
  }
}

class Database(
    context: Context
) : SQLiteOpenHelper(context, "notes.db", null, 1) {

  override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(createNoteSql())
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
  }

  private fun createNoteSql(): String {
    return "create table Note (" +
        "title text," +
        "content text" +
        ")"
  }
}
