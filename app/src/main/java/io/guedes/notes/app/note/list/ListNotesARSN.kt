package io.guedes.notes.app.note.list

import io.guedes.notes.arch.BaseAction
import io.guedes.notes.arch.BaseNavigation
import io.guedes.notes.arch.BaseResult
import io.guedes.notes.arch.BaseState
import io.guedes.notes.domain.model.Note

sealed class ListNotesAction : BaseAction {
    object Init : ListNotesAction()
    object NoteCreated : ListNotesAction()
    data class Delete(val noteId: Long) : ListNotesAction()
    data class UndoDelete(val noteId: Long) : ListNotesAction()
    data class InvertSorting(val descendingSort: Boolean) : ListNotesAction()
}

sealed class ListNotesResult : BaseResult {
    data class Fetch(val notes: List<Note>) : ListNotesResult()
    data class ChangeSorting(val descendingSort: Boolean) : ListNotesResult()
    data class DeleteInProgress(val noteId: Long) : ListNotesResult()
    data class DeleteCompleted(val noteId: Long) : ListNotesResult()
    object DeleteCanceled : ListNotesResult()
}

sealed class ListNotesNavigation : BaseNavigation {
    data class NoteForm(val note: Note?) : ListNotesNavigation()
}

data class ListNotesState(
    val notes: List<Note> = emptyList(),
    val undoDeletionAvailable: Boolean = false,
    val descendingSort: Boolean = true,
    val deleteInProgress: Long = 0
) : BaseState
