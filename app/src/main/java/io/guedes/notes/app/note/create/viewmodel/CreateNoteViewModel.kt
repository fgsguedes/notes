package io.guedes.notes.app.note.create.viewmodel

import io.guedes.notes.app.note.create.interactor.CreateNoteInteractor
import io.guedes.notes.arch.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import io.guedes.notes.app.note.create.CreateNoteAction as Action
import io.guedes.notes.app.note.create.CreateNoteNavigation as Navigation
import io.guedes.notes.app.note.create.CreateNoteResult as Result
import io.guedes.notes.app.note.create.CreateNoteState as State

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteViewModel(
    private val interactor: CreateNoteInteractor,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<Action, Result, State, Navigation>(interactor, dispatcher, State()) {

    init {
        interactor.offer(Action.Init)
    }

    override fun reduce(state: State, result: Result) = when (result) {
        is Result.InputChanged -> onInputChangedResult(state, result.title, result.content)
        is Result.Validation -> onValidationResult(state, result.isValid)
    }

    fun onSave(title: String, content: String) {
        interactor.offer(Action.SaveNote(title, content))
    }

    private fun onInputChangedResult(state: State, title: String, content: String?) =
        state.copy(title = title, content = content)

    private fun onValidationResult(state: State, isValid: Boolean): State =
        state.copy(inputValid = isValid)
}

