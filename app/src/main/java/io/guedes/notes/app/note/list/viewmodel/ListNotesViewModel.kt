package io.guedes.notes.app.note.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListNotesViewModel(
    private val notesRepository: NoteRepository
) : ViewModel() {

    private var currentState = ListNotesState()
    private val state: MutableLiveData<ListNotesState> = MutableLiveData()

    private var deleteJob: Job? = null

    fun state(): LiveData<ListNotesState> = state

    fun onStart() = fetchNotes()

    fun onUpdateSorting() {
        viewModelScope.launch {
            currentState.update {
                it.copy(
                    notes = it.notes.sorted(!it.descendingSort),
                    descendingSort = !it.descendingSort
                )
            }
        }
    }

    fun onItemSwipe(noteId: Long) {
        deleteJob = viewModelScope.launch {
            currentState.update { state ->
                state.copy(
                    notes = state.notes.filterNot { it.id == noteId },
                    undoDeletionAvailable = true
                )
            }
            delay(5000)
            currentState.update { it.copy(undoDeletionAvailable = false) }
            notesRepository.delete(noteId)
        }
    }

    fun onUndoDelete() {
        deleteJob?.cancel()
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            currentState.update {
                it.copy(
                    notes = notesRepository.list()
                        .sorted(currentState.descendingSort)
                )
            }
        }
    }

    private fun List<Note>.sorted(descendingSort: Boolean): List<Note> =
        if (descendingSort) sortedByDescending { it.id }
        else sortedBy { it.id }

    private inline fun ListNotesState.update(updateBlock: (ListNotesState) -> ListNotesState) {
        updateBlock(this).also { newState ->
            currentState = newState
            state.postValue(newState)
        }
    }
}

data class ListNotesState(
    val notes: List<Note> = emptyList(),
    val undoDeletionAvailable: Boolean = false,
    val descendingSort: Boolean = true
)
