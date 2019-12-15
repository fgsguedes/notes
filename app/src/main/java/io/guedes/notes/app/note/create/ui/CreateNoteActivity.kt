package io.guedes.notes.app.note.create.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import io.guedes.notes.app.R
import io.guedes.notes.app.common.stringText
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModel
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModelFactory
import io.guedes.notes.dependencies.provideFactory
import io.guedes.notes.domain.model.Note
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import io.guedes.notes.app.note.create.CreateNoteNavigation as Navigation
import io.guedes.notes.app.note.create.CreateNoteState as State

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteActivity : AppCompatActivity(R.layout.activity_create_note) {

    private val viewModel: CreateNoteViewModel by viewModels {
        provideFactory<CreateNoteViewModelFactory>(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initVm()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ivCreateNote.setOnClickListener {
            viewModel.onSave(
                etNoteTitle.stringText,
                etNoteContent.stringText
            )
        }
    }

    private fun initVm() {
        lifecycleScope.launch {
            launch { viewModel.state().collect { onStateChanged(it) } }
            launch { viewModel.navigation().collect { onNavigate(it) } }
        }
    }

    private fun onStateChanged(state: State) {
        etNoteTitle.setText(state.title)
        etNoteContent.setText(state.content)

        if (!state.inputValid) showValidationSnackbar()
    }

    private fun onNavigate(navigation: Navigation) {
        when (navigation) {
            Navigation.Finish -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun showValidationSnackbar() {
        Snackbar.make(
            createNoteRootView,
            R.string.create_note_fields_required_message,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.create_note_fields_required_action) {}
            .show()
    }

    companion object {
        fun newIntent(activity: Activity, note: Note?) =
            Intent(activity, CreateNoteActivity::class.java).apply {
                this.note = note
            }
    }
}

var Intent.note: Note?
    get() = getParcelableExtra("note")
    set(value) {
        putExtra("note", value)
    }
