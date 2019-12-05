package io.guedes.notes.app.note.list.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import io.guedes.notes.R
import io.guedes.notes.app.note.create.ui.CreateNoteActivity
import io.guedes.notes.app.note.list.viewmodel.ListNotesState
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModel
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModelFactory
import io.guedes.notes.dependencies.provideFactory
import kotlinx.android.synthetic.main.activity_list_notes.clListNotes
import kotlinx.android.synthetic.main.activity_list_notes.fabCreateNote
import kotlinx.android.synthetic.main.activity_list_notes.ivSorting
import kotlinx.android.synthetic.main.activity_list_notes.rvNotes
import kotlinx.android.synthetic.main.activity_list_notes.toolbar

class ListNotesActivity : AppCompatActivity(R.layout.activity_list_notes) {

    private val viewModel: ListNotesViewModel by viewModels {
        provideFactory<ListNotesViewModelFactory>()
    }

    private val adapter by lazy { NotesAdapter() }
    private val swipeListener by lazy { HorizontalSwipeListener() }

    private val undoSnackBar by lazy {
        Snackbar.make(clListNotes, "Deleted", Snackbar.LENGTH_INDEFINITE)
            .setAction("Undo") { viewModel.onUndoDelete() }
    }

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

        ivSorting.setOnClickListener { viewModel.onUpdateSorting() }
        fabCreateNote.setOnClickListener { openCreateNoteForm() }

        val state = bundle?.getParcelable<Parcelable>(KEY_RECYCLER_VIEW_STATE)
        if (state != null) rvNotes.layoutManager?.onRestoreInstanceState(state)

        rvNotes.adapter = adapter
        ItemTouchHelper(swipeListener).attachToRecyclerView(rvNotes)

        swipeListener.swipeId().observe(this, viewModel::onItemSwipe)
    }

    private fun initVm() {
        viewModel.state().observe(this, ::onNewState)
    }

    private fun onNewState(state: ListNotesState) {
        adapter.submitList(state.notes)

        if (state.undoDeletionAvailable) showUndoSnackbar()
        else hideUndoSnackbar()
    }

    private fun showUndoSnackbar() {
        undoSnackBar.show()
    }

    private fun hideUndoSnackbar() {
        undoSnackBar.dismiss()
    }

    private fun openCreateNoteForm() {
        val intent = Intent(this, CreateNoteActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val KEY_RECYCLER_VIEW_STATE = "KEY_RECYCLER_VIEW_STATE"
    }
}
