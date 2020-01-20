package io.guedes.notes.app.note.list.viewmodel

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.guedes.notes.app.note.list.ListNotesState
import io.guedes.notes.app.note.list.interactor.ListNotesInteractor
import io.guedes.notes.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.guedes.notes.app.note.list.ListNotesAction as Action
import io.guedes.notes.app.note.list.ListNotesResult as Result

@FlowPreview
@ExperimentalCoroutinesApi
class ListNotesViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val results = Channel<Result>(Channel.UNLIMITED)
    private val interactor: ListNotesInteractor = mock {
        on { results() }.thenReturn(results.consumeAsFlow())
    }

    private val initialState = ListNotesState()
    private val viewModel = ListNotesViewModel(interactor, dispatcher)

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
        verify(interactor).offer(Action.Init)
    }

    @Test
    fun `onCreateNote Should navigate to note form without any note`() = dispatcher.runBlockingTest {
        // WHEN
        viewModel.onCreateNote()

        // THEN
        verify(interactor).offer(Action.CreateNote)
    }

    @Test
    fun `onNoteCreated Should offer note created action`() {
        // WHEN
        viewModel.onNoteCreated()

        // THEN
        verify(interactor).offer(Action.NoteCreated)
    }

    @Test
    fun `onNoteClick Should navigate to form with given note`() = dispatcher.runBlockingTest {
        // GIVEN
        val note = Note("Note 1", id = 1)

        // WHEN
        viewModel.onNoteClick(note)

        // THEN
        verify(interactor).offer(Action.EditNote(note))
    }

    @Test
    fun `onUpdateSorting With Should offer invert sorting action`() {
        // WHEN
        viewModel.onUpdateSorting()

        // THEN
        verify(interactor).offer(Action.InvertSorting)
    }

    @Test
    fun `onItemSwipe Should offer delete action`() {
        // WHEN
        viewModel.onItemSwipe(1)

        // THEN
        verify(interactor).offer(Action.Delete(1))
    }

    @Test
    fun `onUndoDelete Should offer undo delete action`() {
        // WHEN
        viewModel.onUndoDelete(1)

        // THEN
        verify(interactor).offer(Action.UndoDelete(1))
    }
    // endregion

    // region Results
    @Test
    fun `on Fetch result Should update state list`() = dispatcher.runBlockingTest {
        // GIVEN
        val note1 = Note("First", id = 1)
        val note2 = Note("Second", id = 2)

        val ascendingList = listOf(note1, note2)
        val descendingList = listOf(note2, note1)

        // WHEN
        results.offer(Result.Fetch(ascendingList))

        // THEN
        with(viewModel.state().first()) {
            assertThat(this).isEqualTo(
                initialState.copy(notes = descendingList, descendingSort = true)
            )
        }
    }

    @Test
    fun `on Sorting result Should update state list`() = dispatcher.runBlockingTest {
        // GIVEN
        val note1 = Note("First", id = 1)
        val note2 = Note("Second", id = 2)

        val ascendingList = listOf(note1, note2)

        results.offer(Result.Fetch(ascendingList))

        // WHEN
        results.offer(Result.ChangeSorting(descendingSort = false))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(notes = ascendingList, descendingSort = false)
        )
    }

    @Test
    fun `on Deletion in progress result Should update state`() = dispatcher.runBlockingTest {
        // WHEN
        results.offer(Result.DeleteInProgress(1))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(deleteInProgress = 1)
        )
    }

    @Test
    fun `on Deletion completed result Should clean progress`() = dispatcher.runBlockingTest {
        // GIVEN
        results.offer(Result.DeleteInProgress(1L))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(deleteInProgress = 1)
        )

        // WHEN
        results.offer(Result.DeleteCompleted)

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(initialState)
    }

    @Test
    fun `on Deletion canceled Should clean deletion progress`() = dispatcher.runBlockingTest {
        // WHEN
        results.offer(Result.DeleteInProgress(1))

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(
            initialState.copy(deleteInProgress = 1L)
        )

        // WHEN
        results.offer(Result.DeleteCanceled)

        // THEN
        assertThat(viewModel.latestState()).isEqualTo(initialState)
    }
    // endregion

    private suspend fun ListNotesViewModel.latestState() = state().first()
}
