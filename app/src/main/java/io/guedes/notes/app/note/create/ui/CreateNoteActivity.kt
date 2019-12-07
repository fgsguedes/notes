package io.guedes.notes.app.note.create.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import io.guedes.notes.R
import io.guedes.notes.app.common.stringText
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModel
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModel.Result.CREATED
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModel.Result.INVALID
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModelFactory
import io.guedes.notes.dependencies.provideFactory
import kotlinx.android.synthetic.main.activity_create_note.createNoteRootView
import kotlinx.android.synthetic.main.activity_create_note.etNoteContent
import kotlinx.android.synthetic.main.activity_create_note.etNoteTitle
import kotlinx.android.synthetic.main.activity_create_note.ivCreateNote
import kotlinx.android.synthetic.main.activity_create_note.toolbar

class CreateNoteActivity : AppCompatActivity(R.layout.activity_create_note) {

    private val viewModel: CreateNoteViewModel by viewModels {
        provideFactory<CreateNoteViewModelFactory>()
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
            viewModel.doneClicked(
                etNoteTitle.stringText,
                etNoteContent.stringText
            )
        }
    }

    private fun initVm() {
        viewModel.results().observe(this) { handleResult(it) }
    }

    private fun handleResult(result: CreateNoteViewModel.Result) {
        when (result) {
            CREATED -> {
                setResult(Activity.RESULT_OK)
                finish()
            }
            INVALID -> showValidationSnackbar()
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
}
