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
  private val adapter by lazy { StringArrayAdapter(this) }
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
    recyclerView.adapter = adapter

    adapter.clickListener = { note, position ->
      startActivityForResult(
          Intent(this, CreateNoteActivity::class.java)
              .putExtra("note", note)
              .putExtra("position", position),
          ActivityRequestCodes.editNote
      )
    }
  }

  private fun setUpClickListeners() {
    fab.setOnClickListener {
      startActivityForResult(
          Intent(this, CreateNoteActivity::class.java),
          ActivityRequestCodes.createNote
      )
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    val note = data?.getParcelableExtra<Note>("note")
    val position = data?.getIntExtra("position", 0) as Int

    if (resultCode != RESULT_OK || note == null) {
      super.onActivityResult(requestCode, resultCode, data)
      return
    }

    when (requestCode) {
      ActivityRequestCodes.createNote -> adapter.addElement(note)
      ActivityRequestCodes.editNote -> adapter.updateElement(note, position)
    }
  }
}

//region Adapters
class StringArrayAdapter(
    private val context: Context
) : RecyclerView.Adapter<StringArrayAdapter.StringViewHolder>() {

  val inflater by lazy { LayoutInflater.from(context) }
  val items by lazy { listNotes() }

  var clickListener: ((Note, Int) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StringViewHolder(inflater.inflate(R.layout.adaper_row_list_notes, parent, false))

  override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
    val note = items[position]

    holder.titleTextView.text = note.title
    holder.contentTextView.text = note.content
  }

  override fun getItemCount() = items.size

  private fun listNotes(): MutableList<Note> {
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

    return notes
  }

  fun addElement(note: Note, position: Int = 0) {
    val i = Math.min(position, items.size)
    items.add(i, note)
    notifyItemInserted(i)
  }

  fun updateElement(note: Note, position: Int = 0) {
    val i = Math.min(position, items.size)
    items[i] = note
    notifyItemChanged(i)
  }

  inner class StringViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val titleTextView by lazy { view.findViewById(R.id.adapter_row_list_notes_title) as TextView }
    val contentTextView by lazy { view.findViewById(R.id.adapter_row_list_notes_content) as TextView }

    init {
      view.setOnClickListener {
        val note = items[adapterPosition]
        clickListener?.invoke(note, adapterPosition)
      }
    }
  }
}
//endregion
