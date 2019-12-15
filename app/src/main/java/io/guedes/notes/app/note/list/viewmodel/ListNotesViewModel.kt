package io.guedes.notes.app.note.list.viewmodel

import io.guedes.notes.app.arch.BaseAction
import io.guedes.notes.app.arch.BaseNavigation
import io.guedes.notes.app.arch.BaseResult
import io.guedes.notes.app.arch.BaseState
import io.guedes.notes.app.arch.BaseViewModel
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class Action : BaseAction {
    object Init : Action()
    object NoteCreated : Action()
    data class InvertSorting(val descendingSort: Boolean) : Action()
    data class Delete(val noteId: Long) : Action()
    data class UndoDelete(val noteId: Long) : Action()
}

sealed class Result : BaseResult {
    data class Fetch(val notes: List<Note>) : Result()
    data class InvertSorting(val descendingSort: Boolean) : Result()
    data class DeleteInProgress(val noteId: Long) : Result()
    data class DeleteCompleted(val noteId: Long) : Result()
    object DeleteCanceled : Result()
}

sealed class Navigation : BaseNavigation {
    data class NoteForm(val note: Note?) : Navigation()
}

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesViewModel(
    private val notesRepository: NoteRepository
) : BaseViewModel<Action, Result, ListNotesState, Navigation>(ListNotesState()) {

    init {
        actions.offer(Action.Init)
    }

    fun onCreateNote() {
        navigation.offer(Navigation.NoteForm(note = null))
    }

    fun onNoteCreated() {
        actions.offer(Action.NoteCreated)
    }

    fun onNoteClick(note: Note) {
        navigation.offer(Navigation.NoteForm(note))
    }

    fun onUpdateSorting() {
        actions.offer(Action.InvertSorting(currentState.descendingSort))
    }

    fun onItemSwipe(noteId: Long) {
        actions.offer(Action.Delete(noteId))
    }

    fun onUndoDelete() {
        actions.offer(Action.UndoDelete(currentState.deleteInProgress))
    }

    override fun process(action: Action): Flow<Result> =
        when (action) {
            Action.Init -> onInitAction()
            Action.NoteCreated -> onNoteCreatedAction()
            is Action.InvertSorting -> onInvertSortingAction(action.descendingSort)
            is Action.Delete -> onDeleteAction(action.noteId)
            is Action.UndoDelete -> onUndoDeleteAction(action.noteId)
        }

    override fun reduce(state: ListNotesState, result: Result): ListNotesState =
        when (result) {
            is Result.Fetch -> onFetchResult(state, result.notes)
            is Result.InvertSorting -> onSortingResult(state, result.descendingSort)
            is Result.DeleteInProgress -> onDeleteInProgressResult(state, result.noteId)
            is Result.DeleteCompleted -> onDeleteCompletedResult(state, result.noteId)
            Result.DeleteCanceled -> onDeleteCanceledResult(state)
        }

    // region Actions
    private fun onInitAction() = flow { emit(fetchNotes()) }

    private fun onInvertSortingAction(descendingSort: Boolean) = flow {
        emit(Result.InvertSorting(!descendingSort))
    }

    private fun onDeleteAction(noteId: Long) = flow {
        notesRepository.delete(noteId)
        emit(fetchNotes())
        emit(Result.DeleteInProgress(noteId))
        delay(5000)
        emit(Result.DeleteCompleted(noteId))
    }

    private fun onUndoDeleteAction(noteId: Long) = flow {
        notesRepository.restore(noteId)
        emit(Result.DeleteCanceled)
        emit(fetchNotes())
    }

    private fun onNoteCreatedAction() = flow { emit(fetchNotes()) }
    // endregion

    // region Results
    private fun onFetchResult(state: ListNotesState, notes: List<Note>) =
        state.copy(notes = notes.sorted(state.descendingSort))

    private fun onSortingResult(state: ListNotesState, descendingSort: Boolean) =
        state.copy(
            notes = state.notes.sorted(descendingSort),
            descendingSort = descendingSort
        )

    private fun onDeleteInProgressResult(state: ListNotesState, noteId: Long): ListNotesState {
        return state.copy(
            deleteInProgress = noteId,
            undoDeletionAvailable = true
        )
    }

    private fun onDeleteCompletedResult(state: ListNotesState, noteId: Long): ListNotesState {
        val newDeleteInProgress = state.deleteInProgress.takeIf { it != noteId } ?: 0

        return state.copy(
            deleteInProgress = newDeleteInProgress,
            undoDeletionAvailable = newDeleteInProgress != 0L
        )
    }

    private fun onDeleteCanceledResult(state: ListNotesState) =
        state.copy(
            undoDeletionAvailable = false,
            deleteInProgress = 0
        )
    // endregion

    private fun List<Note>.sorted(descendingSort: Boolean): List<Note> =
        if (descendingSort) sortedByDescending { it.id }
        else sortedBy { it.id }

    private suspend fun fetchNotes() = Result.Fetch(notesRepository.list())
}

data class ListNotesState(
    val notes: List<Note> = emptyList(),
    val undoDeletionAvailable: Boolean = false,
    val descendingSort: Boolean = true,
    val deleteInProgress: Long = 0
) : BaseState
