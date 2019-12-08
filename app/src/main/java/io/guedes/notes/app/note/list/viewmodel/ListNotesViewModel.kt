package io.guedes.notes.app.note.list.viewmodel

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
import kotlinx.coroutines.delay
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
    object Init : Action()
    object CreateNote : Action()
    object NoteCreated : Action()
    object InvertSorting : Action()
    data class Click(val note: Note) : Action()
    data class Delete(val noteId: Long) : Action()
    data class UndoDelete(val noteId: Long) : Action()
}

sealed class Result {
    data class Fetch(val notes: List<Note>) : Result()
    object CreateNote : Result()
    object InvertSorting : Result()
    data class DeleteInProgress(val noteId: Long) : Result()
    data class DeleteCompleted(val noteId: Long) : Result()
    object DeleteCanceled : Result()
    data class EditNote(val note: Note) : Result()
}

sealed class Navigation {
    data class NoteForm(val note: Note?) : Navigation()
}

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesViewModel(
    private val notesRepository: NoteRepository
) : ViewModel() {

    private var currentState: ListNotesState = ListNotesState()
    private val state: MutableLiveData<ListNotesState> = MutableLiveData()
    private val navigation: MutableLiveData<Navigation> = MutableLiveData()

    private val actions: Channel<Action> = Channel(Channel.Factory.CONFLATED)

    init {
        viewModelScope.launch {
            actions.offer(Action.Init)

            actions.consumeAsFlow()
                .flatMapMerge { process(it) }
                .scan(currentState) { state, result -> reduce(state, result) }
                .filter { newState -> currentState != newState }
                .onEach { newState -> currentState = newState }
                .flowOn(Dispatchers.Default)
                .collect { state.postValue(it) }
        }
    }

    fun state(): LiveData<ListNotesState> = state
    fun navigation(): LiveData<Navigation> = navigation

    fun onCreateNote() {
        actions.offer(Action.CreateNote)
    }

    fun onNoteCreated() {
        actions.offer(Action.NoteCreated)
    }

    fun onNoteClick(note: Note) {
        actions.offer(Action.Click(note))
    }

    fun onUpdateSorting() {
        actions.offer(Action.InvertSorting)
    }

    fun onItemSwipe(noteId: Long) {
        actions.offer(Action.Delete(noteId))
    }

    fun onUndoDelete() {
        actions.offer(Action.UndoDelete(currentState.deleteInProgress))
    }

    private fun process(action: Action): Flow<Result> =
        when (action) {
            Action.Init -> onInitAction()
            Action.CreateNote -> onCreateNoteAction()
            Action.NoteCreated -> onNoteCreatedAction()
            Action.InvertSorting -> onInvertSortingAction()
            is Action.Click -> onClickAction(action.note)
            is Action.Delete -> onDeleteAction(action.noteId)
            is Action.UndoDelete -> onUndoDeleteAction(action.noteId)
        }

    private fun reduce(state: ListNotesState, result: Result): ListNotesState =
        when (result) {
            is Result.Fetch -> onFetchResult(state, result.notes)
            Result.CreateNote -> onCreateNoteResult(state)
            Result.InvertSorting -> onSortingResult(state)
            is Result.DeleteInProgress -> onDeleteInProgressResult(state, result.noteId)
            is Result.DeleteCompleted -> onDeleteCompletedResult(state, result.noteId)
            Result.DeleteCanceled -> onDeleteCanceledResult(state)
            is Result.EditNote -> onEditNoteResult(state, result.note)
        }

    // region Actions
    private fun onInitAction() = flow { emit(fetchNotes()) }

    private fun onCreateNoteAction() = flow {
        emit(Result.CreateNote)
    }

    private fun onInvertSortingAction() = flow {
        emit(Result.InvertSorting)
    }

    private fun onClickAction(note: Note) = flow {
        emit(Result.EditNote(note))
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

    private fun onCreateNoteResult(state: ListNotesState) = state.also {
        navigation.postValue(Navigation.NoteForm(note = null))
    }

    private fun onSortingResult(state: ListNotesState) =
        state.copy(
            notes = state.notes.sorted(!state.descendingSort),
            descendingSort = !state.descendingSort
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

    private fun onEditNoteResult(state: ListNotesState, note: Note) = state.also {
        navigation.postValue(Navigation.NoteForm(note))
    }
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
)
