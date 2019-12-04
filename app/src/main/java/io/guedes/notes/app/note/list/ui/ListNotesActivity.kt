package io.guedes.notes.app.note.list.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import io.guedes.notes.R
import io.guedes.notes.app.note.create.ui.CreateNoteActivity
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModel
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModelFactory
import io.guedes.notes.dependencies.provideFactory
import kotlinx.android.synthetic.main.activity_list_notes.fabCreateNote
import kotlinx.android.synthetic.main.activity_list_notes.ivSorting
import kotlinx.android.synthetic.main.activity_list_notes.rvNotes
import kotlinx.android.synthetic.main.activity_list_notes.toolbar

class ListNotesActivity : AppCompatActivity(R.layout.activity_list_notes) {

    private val viewModel: ListNotesViewModel by viewModels {
        provideFactory<ListNotesViewModelFactory>()
    }

    private val adapter by lazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi(savedInstanceState)
        initVm()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
            KEY_RECYCLER_VIEW_STATE,
            rvNotes.layoutManager?.onSaveInstanceState()
        )
        super.onSaveInstanceState(outState)
    }

    private fun initUi(bundle: Bundle?) {
        setSupportActionBar(toolbar)

        ivSorting.setOnClickListener { viewModel.onSorting() }
        fabCreateNote.setOnClickListener { openCreateNoteForm() }

        val state = bundle?.getParcelable<Parcelable>(KEY_RECYCLER_VIEW_STATE)
        if (state != null) rvNotes.layoutManager?.onRestoreInstanceState(state)

        rvNotes.adapter = adapter
    }

    private fun initVm() {
        viewModel.notes().observe(this, adapter::submitList)
    }

    private fun openCreateNoteForm() {
        val intent = Intent(this, CreateNoteActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val KEY_RECYCLER_VIEW_STATE = "KEY_RECYCLER_VIEW_STATE"
    }
}
