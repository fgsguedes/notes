package io.guedes.notes.app.note.list.viewmodel

import io.guedes.notes.app.note.list.interactor.ListNotesInteractor
import io.guedes.notes.arch.BaseViewModel
import io.guedes.notes.domain.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import io.guedes.notes.app.note.list.ListNotesAction as Action
import io.guedes.notes.app.note.list.ListNotesNavigation as Navigation
import io.guedes.notes.app.note.list.ListNotesResult as Result
import io.guedes.notes.app.note.list.ListNotesState as State

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesViewModel(
    private val interactor: ListNotesInteractor,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<Action, Result, State, Navigation>(interactor, dispatcher, State()) {

    init {
        interactor.offer(Action.Init)
    }

    override fun reduce(state: State, result: Result) = when (result) {
        is Result.Fetch -> onFetchResult(state, result.notes)
        is Result.ChangeSorting -> onSortingResult(state, result.descendingSort)
        is Result.DeleteInProgress -> onDeleteInProgressResult(state, result.noteId)
        is Result.DeleteCompleted -> onDeleteCompletedResult(state, result.noteId)
        Result.DeleteCanceled -> onDeleteCanceledResult(state)
    }

    fun onCreateNote() {
        navigate(Navigation.NoteForm(note = null))
    }

    fun onNoteCreated() {
        interactor.offer(Action.NoteCreated)
    }

    fun onNoteClick(note: Note) {
        navigate(Navigation.NoteForm(note))
    }

    fun onUpdateSorting() {
        interactor.offer(Action.InvertSorting(currentState.descendingSort))
    }

    fun onItemSwipe(noteId: Long) {
        interactor.offer(Action.Delete(noteId))
    }

    fun onUndoDelete() {
        interactor.offer(Action.UndoDelete(currentState.deleteInProgress))
    }

    // region Results
    private fun onFetchResult(state: State, notes: List<Note>) =
        state.copy(notes = notes.sorted(state.descendingSort))

    private fun onSortingResult(state: State, descendingSort: Boolean) =
        state.copy(
            notes = state.notes.sorted(descendingSort),
            descendingSort = descendingSort
        )

    private fun onDeleteInProgressResult(state: State, noteId: Long) =
        state.copy(
            deleteInProgress = noteId,
            undoDeletionAvailable = true
        )

    private fun onDeleteCompletedResult(state: State, noteId: Long): State {
        val newDeleteInProgress = state.deleteInProgress.takeIf { it != noteId } ?: 0

        return state.copy(
            deleteInProgress = newDeleteInProgress,
            undoDeletionAvailable = newDeleteInProgress != 0L
        )
    }

    private fun onDeleteCanceledResult(state: State) =
        state.copy(
            undoDeletionAvailable = false,
            deleteInProgress = 0
        )
    // endregion

    private fun List<Note>.sorted(descendingSort: Boolean): List<Note> =
        if (descendingSort) sortedByDescending { it.id }
        else sortedBy { it.id }
}
