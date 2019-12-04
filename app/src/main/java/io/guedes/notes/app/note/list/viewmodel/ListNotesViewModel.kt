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
    fun notes(): LiveData<List<Note>> = notes

    fun onStart() {
        viewModelScope.launch {
            notes.postValue(notesRepository.list())
        }
    }
}
