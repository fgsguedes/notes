package io.guedes.notes.app.note.create

import io.guedes.notes.arch.BaseAction
import io.guedes.notes.arch.BaseNavigation
import io.guedes.notes.arch.BaseResult
import io.guedes.notes.arch.BaseState

sealed class CreateNoteAction : BaseAction {
    object Init : CreateNoteAction()
    data class SaveNote(val title: String, val content: String) : CreateNoteAction()
}

sealed class CreateNoteResult : BaseResult {
    data class InputChanged(val title: String, val content: String?) : CreateNoteResult()
    data class Validation(val isValid: Boolean) : CreateNoteResult()
}

sealed class CreateNoteNavigation : BaseNavigation {
    object Finish : CreateNoteNavigation()
}

data class CreateNoteState(
    val title: String = "",
    val content: String? = null,
    val inputValid: Boolean = true
) : BaseState
