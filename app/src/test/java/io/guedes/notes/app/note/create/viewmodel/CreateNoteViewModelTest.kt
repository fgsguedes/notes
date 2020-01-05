package io.guedes.notes.app.note.create.viewmodel

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.guedes.notes.app.note.create.interactor.CreateNoteInteractor
import io.guedes.notes.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.guedes.notes.app.note.create.CreateNoteAction as Action
import io.guedes.notes.app.note.create.CreateNoteNavigation as Navigation
import io.guedes.notes.app.note.create.CreateNoteResult as Result
import io.guedes.notes.app.note.create.CreateNoteState as State

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val results = ConflatedBroadcastChannel<Result>()
    private val interactor: CreateNoteInteractor = mock {
        on { results() }.thenReturn(results.asFlow())
    }

    private val initialState = State()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun cleanup() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    // region Actions
    @Test
    fun `on init With null note Should offer Init action with null note`() {
        // GIVEN
        createVM(note = null)

        // THEN
        verify(interactor).offer(Action.Init(note = null))
    }

    @Test
    fun `on init Should offer Init action`() {
        // GIVEN
        val note = Note("Note 1", id = 1L)
        createVM(note)

        // THEN
        verify(interactor).offer(Action.Init(note))
    }

    @Test
    fun `onSave Should offer InputChanged action`() {
        // GIVEN
        val viewModel = createVM()

        // WHEN
        viewModel.onSave("Title", "Content")

        // THEN
        verify(interactor).offer(Action.InputChanged(0, "Title", "Content"))
    }

    @Test
    fun `onSave With note Should offer Init action with id`() = runBlockingTest {
        // GIVEN
        val viewModel = createVM()
        results.offer(Result.InitResult(1L))

        // WHEN
        viewModel.onSave("Title", "Content")

        // THEN
        verify(interactor).offer(Action.InputChanged(1L, "Title", "Content"))
    }
    // endregion

//    is Result.InitResult -> onInitResult(state, result.noteId)
//    is Result.InputChanged -> onInputChangedResult(state, result.title, result.content)
//    is Result.Validation -> onValidationResult(state, result.isValid)
//    Result.NoteCreated -> onNoteCreatedResult(state)

    // region Results
    @Test
    fun `on InitResult Should update state`() = runBlockingTest {
        // GIVEN
        val viewModel = createVM()

        // WHEN
        results.offer(Result.InitResult(1L))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(noteId = 1L)
        )
    }

    @Test
    fun `on InputChanged result Should update state`() = runBlockingTest {
        // GIVEN
        val viewModel = createVM()

        // WHEN
        results.offer(Result.InputChanged("Title", "Content"))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(title = "Title", content = "Content")
        )
    }

    @Test
    fun `on ValidationResult With invalid input Should update state`() = runBlockingTest {
        // GIVEN
        val viewModel = createVM()

        // WHEN
        results.offer(Result.Validation(isValid = false))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(inputValid = false)
        )
    }

    @Test
    fun `on ValidationResult With valid input Should update state`() = runBlockingTest {
        // GIVEN
        val viewModel = createVM()

        // WHEN
        results.offer(Result.Validation(isValid = true))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(inputValid = true)
        )
    }

    @Test
    fun `on NoteCreatedResult Should finish`() = runBlockingTest {
        // GIVEN
        val viewModel = createVM()

        // WHEN
        results.offer(Result.NoteCreated)

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(initialState)
        assertThat(viewModel.latestNavigation()).isEqualTo(Navigation.Finish)
    }
    // endregion

    private fun createVM(note: Note? = null) = CreateNoteViewModel(interactor, dispatcher, note)

    private suspend fun CreateNoteViewModel.latestState() = state().first()
    private suspend fun CreateNoteViewModel.latestNavigation() = navigation().first()
}
