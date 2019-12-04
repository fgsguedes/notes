package io.guedes.notes.app.note.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class ListNotesViewModel(
    private val notesRepository: NoteRepository
) : ViewModel() {

    private val notes: MutableLiveData<List<Note>> = MutableLiveData()

    private var descending = true

    fun notes(): LiveData<List<Note>> = notes

    fun onStart() {
        viewModelScope.launch {
            updateList()
        }
    }

    fun onSorting() {
        this.descending = !descending
        viewModelScope.launch {
            updateList()
        }
    }

    fun onItemSwipe(noteId: Long) {
        viewModelScope.launch {
            notesRepository.delete(noteId)
            updateList()
        }
    }

    private suspend fun updateList() {
        val noteList = with(notesRepository.list()) {
            if (descending) sortedByDescending { it.id }
            else sortedBy { it.id }
        }

        notes.postValue(noteList)
    }
}
