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
class StringArrayAdapter(context: Context) : RecyclerView.Adapter<StringViewHolder>() {

  val inflater by lazy { LayoutInflater.from(context) }
  val items = arrayOf("First item", "Second item", "Third item", "Fourth item")

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StringViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false))

  override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
    holder.textView.text = items[position]
  }

  override fun getItemCount() = items.size
}

class StringViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val textView by lazy { view.findViewById(android.R.id.text1) as TextView }
}
//endregion
