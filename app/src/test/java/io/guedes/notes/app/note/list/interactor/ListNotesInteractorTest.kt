package io.guedes.notes.app.note.list.interactor

import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
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
class ListNotesInteractorTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val dummyList: List<Note> = mock()
    private val repository: NoteRepository = mock {
        onBlocking { list() }.thenReturn(dummyList)
    }
    private val interactor = ListNotesInteractor(repository)


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun cleanup() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on Init action Should fetch notes`() = runBlockingTest {
        // WHEN
        interactor.offer(Action.Init)

        // THEN
        assertThat(interactor.latestResult()).isEqualTo(
            Result.Fetch(dummyList)
        )
        verify(repository).list()
    }

    @Test
    fun `on NoteCreated action Should fetch notes`() = runBlockingTest {
        // WHEN
        interactor.offer(Action.NoteCreated)

        // THEN
        assertThat(interactor.latestResult()).isEqualTo(
            Result.Fetch(dummyList)
        )
    }

    @Test
    fun `on InvertSorting action Should change sorting`() = runBlockingTest {
        // WHEN
        interactor.offer(Action.InvertSorting)
        interactor.offer(Action.InvertSorting)
        interactor.offer(Action.InvertSorting)
        interactor.offer(Action.InvertSorting)

        // THEN
        assertThat(interactor.latestResults(4)).isEqualTo(
            listOf(
                Result.ChangeSorting(descendingSort = false),
                Result.ChangeSorting(descendingSort = true),
                Result.ChangeSorting(descendingSort = false),
                Result.ChangeSorting(descendingSort = true)
            )
        )
    }

    @Test
    fun `on DeleteNote action Should delete note`() = dispatcher.runBlockingTest {
        // WHEN
        interactor.offer(Action.Delete(1L))

        // THEN
        assertThat(interactor.latestResults(3)).isEqualTo(
            listOf(
                Result.Fetch(dummyList),
                Result.DeleteInProgress(1L),
                Result.DeleteCompleted
            )
        )

        repository.inOrder {
            verify().delete(1L)
            verify().list()
        }
    }

    @Test
    fun `on UndoDelete action Should restore note`() = runBlockingTest {
        // WHEN
        interactor.offer(Action.UndoDelete(noteId = 1L))

        // THEN
        assertThat(interactor.latestResults(2)).isEqualTo(
            listOf(
                Result.DeleteCanceled,
                Result.Fetch(dummyList)
            )
        )
        verify(repository).restore(1L)
        verify(repository).list()
    }

    private suspend fun ListNotesInteractor.latestResult() = results().first()
    private suspend fun ListNotesInteractor.latestResults(amount: Int) =
        results().take(amount).toList()
}
