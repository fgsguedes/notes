package io.guedes.notes.app.note.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.guedes.notes.domain.repository.NoteRepository

class CreateNoteViewModelFactory(
    private val noteRepository: NoteRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CreateNoteViewModel(noteRepository) as T
}
