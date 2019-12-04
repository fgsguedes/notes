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

    fun onStart() = updateList()

    fun onSorting() = updateList(!descending)

    private fun updateList(descending: Boolean = true) {
        this.descending = descending

        viewModelScope.launch {
            val noteList = with(notesRepository.list()) {
                if (descending) sortedByDescending { it.id }
                else sortedBy { it.id }
            }

            notes.postValue(noteList)
        }
    }
}
