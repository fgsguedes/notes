package justmakeitwork

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.domain.model.Note

class ListNotesActivity : AppCompatActivity() {

  //region Fields
  private val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
  private val recyclerView by lazy { findViewById(R.id.recycler_notes_list) as RecyclerView }
  private val fab by lazy { findViewById(R.id.fab) as FloatingActionButton }
  //endregion

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_notes)
    setSupportActionBar(toolbar)
    showList()
    setUpClickListeners()
  }

  private fun showList() {
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = StringArrayAdapter(this)
  }

  private fun setUpClickListeners() {
    fab.setOnClickListener { startActivity(Intent(this, CreateNoteActivity::class.java)) }
  }
}

//region Adapters
class StringArrayAdapter(
    private val context: Context
) : RecyclerView.Adapter<StringViewHolder>() {

  val inflater by lazy { LayoutInflater.from(context) }
  val items by lazy { listNotes() }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StringViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false))

  override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
    holder.textView.text = items[position].title
  }

  override fun getItemCount() = items.size

  private fun listNotes(): Array<Note> {
    val databaseHelper = DatabaseHelper(context)
    val database = databaseHelper.readableDatabase

    val cursor = database.query("Note",
        arrayOf("id", "title", "content"),
        null, null, null, null, "id desc", null)

    val notes = mutableListOf<Note>()

    while (cursor.moveToNext()) {
      notes.add(Note(
          cursor.getLong(cursor.getColumnIndex("id")),
          cursor.getString(cursor.getColumnIndex("title")),
          cursor.getString(cursor.getColumnIndex("content"))
      ))
    }

    return notes.toTypedArray()
  }
}

class StringViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val textView by lazy { view.findViewById(android.R.id.text1) as TextView }
}
//endregion
