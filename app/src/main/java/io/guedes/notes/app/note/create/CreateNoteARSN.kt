package io.guedes.notes.app.note.create

import io.guedes.notes.app.arch.BaseAction
import io.guedes.notes.app.arch.BaseNavigation
import io.guedes.notes.app.arch.BaseResult
import io.guedes.notes.app.arch.BaseState
import io.guedes.notes.domain.model.Note

sealed class CreateNoteAction : BaseAction {
    data class Init(val note: Note?) : CreateNoteAction()

    data class InputChanged(
        val noteId: Long,
        val title: String,
        val content: String
    ) : CreateNoteAction()
}

sealed class CreateNoteResult : BaseResult {
    data class InitResult(val noteId: Long) : CreateNoteResult()
    data class InputChanged(val title: String, val content: String?) : CreateNoteResult()
    data class Validation(val isValid: Boolean) : CreateNoteResult()
    object NoteCreated : CreateNoteResult()
}

sealed class CreateNoteNavigation : BaseNavigation {
    object Finish : CreateNoteNavigation()
}

data class CreateNoteState(
    val noteId: Long = 0,
    val title: String = "",
    val content: String? = null,
    val inputValid: Boolean = true
) : BaseState
