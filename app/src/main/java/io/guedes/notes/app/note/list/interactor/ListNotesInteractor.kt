package io.guedes.notes.app.note.list.interactor

import io.guedes.notes.app.arch.BaseInteractor
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import io.guedes.notes.app.note.list.ListNotesAction as Action
import io.guedes.notes.app.note.list.ListNotesResult as Result

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesInteractor(
    private val notesRepository: NoteRepository
) : BaseInteractor<Action, Result>() {

    override fun process(action: Action) = when (action) {
        Action.Init -> onInitAction()
        Action.NoteCreated -> onNoteCreatedAction()
        is Action.InvertSorting -> onInvertSortingAction(action.descendingSort)
        is Action.Delete -> onDeleteAction(action.noteId)
        is Action.UndoDelete -> onUndoDeleteAction(action.noteId)
    }

    private fun onInitAction() = flow { emit(fetchNotesResult()) }

    private fun onInvertSortingAction(descendingSort: Boolean) = flow {
        emit(Result.InvertSorting(!descendingSort))
    }

    private fun onDeleteAction(noteId: Long) = flow {
        notesRepository.delete(noteId)

        emit(fetchNotesResult())
        emit(Result.DeleteInProgress(noteId))

        delay(5000)

        emit(Result.DeleteCompleted(noteId))
    }

    private fun onUndoDeleteAction(noteId: Long) = flow {
        notesRepository.restore(noteId)

        emit(Result.DeleteCanceled)
        emit(fetchNotesResult())
    }

    private fun onNoteCreatedAction() = flow { emit(fetchNotesResult()) }

    private suspend fun fetchNotesResult() = Result.Fetch(notesRepository.list())
}
