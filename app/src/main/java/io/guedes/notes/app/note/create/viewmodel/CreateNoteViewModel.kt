package io.guedes.notes.app.note.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

sealed class Action {
    data class Init(val note: Note?) : Action()

    data class InputChanged(
        val noteId: Long,
        val title: String,
        val content: String
    ) : Action()
}

sealed class Result {
    data class InitResult(val noteId: Long) : Result()
    data class InputChanged(val title: String, val content: String?) : Result()
    data class Validation(val isValid: Boolean) : Result()
    object NoteCreated : Result()
}

sealed class Navigation {
    object Finish : Navigation()
}

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteViewModel(
    private val noteRepository: NoteRepository,
    note: Note?
) : ViewModel() {

    private var currentState = CreateNoteState()
    private val state = MutableLiveData<CreateNoteState>()
    private val navigation = MutableLiveData<Navigation>()

    private val actions = Channel<Action>(Channel.CONFLATED)

    init {
        viewModelScope.launch {
            actions.offer(Action.Init(note))

            actions.consumeAsFlow()
                .flatMapMerge { process(it) }
                .scan(currentState) { state, result -> reduce(state, result) }
                .filter { newState -> currentState != newState }
                .onEach { newState -> currentState = newState }
                .flowOn(Dispatchers.Default)
                .collect { state.postValue(it) }
        }
    }

    fun state(): LiveData<CreateNoteState> = state
    fun navigation(): LiveData<Navigation> = navigation

    fun onSave(title: String, content: String) {
        actions.offer(Action.InputChanged(currentState.noteId, title, content))
    }

    private fun process(action: Action) =
        when (action) {
            is Action.Init -> onInitAction(action.note)
            is Action.InputChanged ->
                onInputChangedAction(action.noteId, action.title, action.content)
        }

    private fun reduce(state: CreateNoteState, result: Result) =
        when (result) {
            is Result.InitResult -> onInitResult(state, result.noteId)
            is Result.InputChanged -> onInputChangedResult(state, result.title, result.content)
            is Result.Validation -> onValidationResult(state, result.isValid)
            Result.NoteCreated -> onNoteCreatedResult(state)
        }

    // region Actions
    private fun onInitAction(note: Note?) = flow {
        if (note != null) {
            emit(Result.InitResult(note.id))
            emit(Result.InputChanged(note.title, note.content))
        }
    }

    private fun onInputChangedAction(
        noteId: Long,
        title: String,
        content: String
    ): Flow<Result> = flow {

        val isValid = title.isNotBlank()
        val formattedContent = content.takeIf { it.isNotBlank() }

        emit(Result.InputChanged(title, formattedContent))
        emit(Result.Validation(isValid = isValid))

        if (isValid) {
            val note = Note(id = noteId, title = title, content = formattedContent)
            noteRepository.save(note)
            emit(Result.NoteCreated)
        }
    }
    // endregion

    // region Results
    private fun onInitResult(state: CreateNoteState, noteId: Long) =
        state.copy(noteId = noteId)

    private fun onInputChangedResult(state: CreateNoteState, title: String, content: String?) =
        state.copy(title = title, content = content)

    private fun onValidationResult(state: CreateNoteState, isValid: Boolean): CreateNoteState =
        state.copy(inputValid = isValid)

    private fun onNoteCreatedResult(state: CreateNoteState) = state.also {
        navigation.postValue(Navigation.Finish)
    }
    // endregion
}

data class CreateNoteState(
    val noteId: Long = 0,
    val title: String = "",
    val content: String? = null,
    val inputValid: Boolean = true
)
