package io.guedes.notes.app.note.presenter

import io.guedes.notes.app.note.view.CreateNoteView
import io.guedes.notes.domain.repository.NoteRepository
import timber.log.Timber
import javax.inject.Inject

class CreateNotePresenter @Inject constructor(
    private val view: CreateNoteView,
    private val noteRepository: NoteRepository
) {

    fun doneClicked(title: String, content: String) {

        if (title.isNotBlank() && content.isNotBlank()) {
            noteRepository.create(title, content).subscribe(
                { view.noteCreated(it) },
                { Timber.e(it, "Unable to create note") }
            )

        } else {
            view.invalidForm()
        }
    }
}