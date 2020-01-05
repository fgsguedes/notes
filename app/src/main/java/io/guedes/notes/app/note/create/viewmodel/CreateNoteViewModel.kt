package io.guedes.notes.app.note.create.viewmodel

import io.guedes.notes.app.note.create.interactor.CreateNoteInteractor
import io.guedes.notes.arch.BaseViewModel
import io.guedes.notes.domain.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    note: Note?,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseViewModel<Action, Result, State, Navigation>(interactor, dispatcher, State()) {

    init {
        interactor.offer(Action.Init(note))
    }

    override fun reduce(state: State, result: Result) = when (result) {
        is Result.InitResult -> onInitResult(state, result.noteId)
        is Result.InputChanged -> onInputChangedResult(state, result.title, result.content)
        is Result.Validation -> onValidationResult(state, result.isValid)
        Result.NoteCreated -> onNoteCreatedResult(state)
    }

    fun onSave(title: String, content: String) {
        interactor.offer(Action.InputChanged(currentState.noteId, title, content))
    }

    private fun onInitResult(state: State, noteId: Long) =
        state.copy(noteId = noteId)

    private fun onInputChangedResult(state: State, title: String, content: String?) =
        state.copy(title = title, content = content)

    private fun onValidationResult(state: State, isValid: Boolean): State =
        state.copy(inputValid = isValid)

    private fun onNoteCreatedResult(state: State) = state.also {
        navigate(Navigation.Finish)
    }
}

