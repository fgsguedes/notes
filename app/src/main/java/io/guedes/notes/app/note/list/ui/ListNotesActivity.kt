package io.guedes.notes.app.note.list.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import io.guedes.notes.R
import io.guedes.notes.app.note.create.ui.CreateNoteActivity
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModel
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModelFactory
import io.guedes.notes.dependencies.provideFactory
import io.guedes.notes.domain.model.Note
import kotlinx.android.synthetic.main.activity_list_notes.clListNotes
import kotlinx.android.synthetic.main.activity_list_notes.fabCreateNote
import kotlinx.android.synthetic.main.activity_list_notes.ivSorting
import kotlinx.android.synthetic.main.activity_list_notes.rvNotes
import kotlinx.android.synthetic.main.activity_list_notes.toolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import io.guedes.notes.app.note.list.ListNotesNavigation as Navigation
import io.guedes.notes.app.note.list.ListNotesState as State

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesActivity : AppCompatActivity(R.layout.activity_list_notes) {

    private val viewModel: ListNotesViewModel by viewModels {
        provideFactory<ListNotesViewModelFactory>()
    }

    private val adapter by lazy { NotesAdapter() }
    private val swipeListener by lazy { HorizontalSwipeListener() }

    private var deleteInProgress: Long? = null
    private var undoSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi(savedInstanceState)
        initVm()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(
            KEY_RECYCLER_VIEW_STATE,
            rvNotes.layoutManager?.onSaveInstanceState()
        )
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == createNoteRequestCode && resultCode == Activity.RESULT_OK) {
            viewModel.onNoteCreated()
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initUi(bundle: Bundle?) {
        setSupportActionBar(toolbar)

        ivSorting.setOnClickListener { viewModel.onUpdateSorting() }
        fabCreateNote.setOnClickListener { viewModel.onCreateNote() }

        val state = bundle?.getParcelable<Parcelable>(KEY_RECYCLER_VIEW_STATE)
        if (state != null) rvNotes.layoutManager?.onRestoreInstanceState(state)

        rvNotes.adapter = adapter
        ItemTouchHelper(swipeListener).attachToRecyclerView(rvNotes)

        swipeListener.swipes().observe(this, viewModel::onItemSwipe)
        adapter.clicks().observe(this, viewModel::onNoteClick)
    }

    private fun initVm() {
        lifecycleScope.launch {
            launch { viewModel.state().collect { onStateChanged(it) } }
            launch { viewModel.navigation().collect { onNavigation(it) } }
        }
    }

    private fun onStateChanged(state: State) {
        adapter.submitList(state.notes)

        if (state.undoDeletionAvailable) showUndoSnackbar(state.deleteInProgress)
        else hideUndoSnackbar()
    }

    private fun onNavigation(navigation: Navigation) = when (navigation) {
        is Navigation.NoteForm -> openCreateNoteForm(navigation.note)
    }

    private fun showUndoSnackbar(deleteInProgress: Long) {
        if (this.deleteInProgress == deleteInProgress) return

        this.deleteInProgress = deleteInProgress

        undoSnackBar?.dismiss()
        undoSnackBar = Snackbar.make(clListNotes, "Deleted", Snackbar.LENGTH_INDEFINITE)
            .setAction("Undo") { viewModel.onUndoDelete() }
            .also { it.show() }
    }

    private fun hideUndoSnackbar() {
        deleteInProgress = null
        undoSnackBar?.dismiss()
    }

    private fun openCreateNoteForm(note: Note?) {
        val intent = CreateNoteActivity.newIntent(this, note)
        startActivityForResult(intent, createNoteRequestCode)
    }

    companion object {
        const val createNoteRequestCode = 1
        const val KEY_RECYCLER_VIEW_STATE = "KEY_RECYCLER_VIEW_STATE"
    }
}
