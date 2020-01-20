package io.guedes.notes.app.note.create.viewmodel

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.guedes.notes.app.note.create.interactor.CreateNoteInteractor
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
    private val viewModel = CreateNoteViewModel(interactor, dispatcher)

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
    fun `on init Should offer Init action`() {
        // THEN
        verify(interactor).offer(Action.Init)
    }

    @Test
    fun `onSave Should offer InputChanged action`() {
        // WHEN
        viewModel.onSave("Title", "Content")

        // THEN
        verify(interactor).offer(Action.SaveNote("Title", "Content"))
    }
    // endregion

    // region Results
    @Test
    fun `on InputChanged result Should update state`() = runBlockingTest {
        // WHEN
        results.offer(Result.InputChanged("Title", "Content"))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(title = "Title", content = "Content")
        )
    }

    @Test
    fun `on ValidationResult With invalid input Should update state`() = runBlockingTest {
        // WHEN
        results.offer(Result.Validation(isValid = false))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(inputValid = false)
        )
    }

    @Test
    fun `on ValidationResult With valid input Should update state`() = runBlockingTest {
        // WHEN
        results.offer(Result.Validation(isValid = true))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(inputValid = true)
        )
    }
    // endregion

    private suspend fun CreateNoteViewModel.latestState() = state().first()
}
