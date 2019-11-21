package io.guedes.notes.app.note.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository

class ListNotesViewModel(
    private val notesRepository: NoteRepository
) : ViewModel() {

    val notes: LiveData<List<Note>> = liveData {
        emit(notesRepository.list())
    }
}

