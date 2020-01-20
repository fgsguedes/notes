package io.guedes.notes.app.note.list.interactor

import io.guedes.notes.arch.BaseInteractor
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicLong
import io.guedes.notes.app.note.list.ListNotesAction as Action
import io.guedes.notes.app.note.list.ListNotesNavigation as Navigation
import io.guedes.notes.app.note.list.ListNotesResult as Result

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesInteractor(
    private val notesRepository: NoteRepository
) : BaseInteractor<Action, Result, Navigation>() {

    private var descendingSort: Boolean = true
    private val deleteInProgress: AtomicLong = AtomicLong()

    override fun process(action: Action) = when (action) {
        Action.Init -> onInitAction()
        Action.CreateNote -> onCreateNoteAction()
        Action.NoteCreated -> onNoteCreatedAction()
        is Action.EditNote -> onEditNote(action.note)
        is Action.Delete -> onDeleteAction(action.noteId)
        is Action.UndoDelete -> onUndoDeleteAction(action.noteId)
        is Action.InvertSorting -> onInvertSortingAction()
    }

    private fun onInitAction() = flow { emit(fetchNotesResult()) }

    private fun onCreateNoteAction(): Flow<Result> {
        offer(Navigation.NoteForm(note = null))
        return emptyFlow()
    }

    private fun onNoteCreatedAction() = flow { emit(fetchNotesResult()) }

    private fun onEditNote(note: Note): Flow<Result> {
        offer(Navigation.NoteForm(note))
        return emptyFlow()
    }

    private fun onInvertSortingAction() = flow {
        descendingSort = !descendingSort
        emit(Result.ChangeSorting(descendingSort))
    }

    private fun onDeleteAction(noteId: Long) = flow {
        notesRepository.delete(noteId)
        deleteInProgress.set(noteId)

        emit(fetchNotesResult())
        emit(Result.DeleteInProgress(noteId))

        delay(5000)

        if (deleteInProgress.get() == noteId) {
            emit(Result.DeleteCompleted)
            deleteInProgress.set(0)
        }
    }

    private fun onUndoDeleteAction(noteId: Long) = flow {
        notesRepository.restore(noteId)

        emit(Result.DeleteCanceled)
        emit(fetchNotesResult())
    }

    private suspend fun fetchNotesResult() = Result.Fetch(notesRepository.list())
}
