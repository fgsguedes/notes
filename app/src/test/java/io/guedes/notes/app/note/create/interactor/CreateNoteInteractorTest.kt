package io.guedes.notes.app.note.create.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
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
import io.guedes.notes.app.note.create.CreateNoteAction as Action
import io.guedes.notes.app.note.create.CreateNoteNavigation as Navigation
import io.guedes.notes.app.note.create.CreateNoteResult as Result

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteInteractorTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val repository: NoteRepository = mock()

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
    fun `on Init action`() = runBlockingTest {
        // GIVEN
        val note = Note(id = 1L, title = "Title", content = "Content")
        val interactor = createInteractor(note)

        // WHEN
        interactor.offer(Action.Init)

        // THEN
        assertThat(interactor.latestResult()).isEqualTo(
            Result.InputChanged(title = "Title", content = "Content")
        )
    }

    @Test
    fun `on InputChanged action With valid data Should save note`() = runBlockingTest {
        // GIVEN
        val note = Note(id = 1L, title = "Title", content = "Content")
        val interactor = createInteractor(note)

        // WHEN
        interactor.offer(Action.SaveNote("Title", "Content"))

        // THEN
        assertThat(interactor.latestResults(2)).isEqualTo(
            listOf(
                Result.InputChanged("Title", "Content"),
                Result.Validation(isValid = true)
            )
        )
        assertThat(interactor.latestNavigation()).isEqualTo(
            Navigation.Finish
        )
        verify(repository).save(note)
    }

    @Test
    fun `on InputChanged action With invalid data Should not save note`() = runBlockingTest {
        // GIVEN
        val interactor = createInteractor()

        // WHEN
        interactor.offer(Action.SaveNote("", "Content"))

        // THEN
        assertThat(interactor.latestResults(2)).isEqualTo(
            listOf(
                Result.InputChanged("", "Content"),
                Result.Validation(isValid = false)
            )
        )
        verifyNoMoreInteractions(repository)
    }

    private fun createInteractor(note: Note? = null) =
        CreateNoteInteractor(repository, note)

    private suspend fun CreateNoteInteractor.latestResult() = results().first()
    private suspend fun CreateNoteInteractor.latestResults(amount: Int) = results().take(amount).toList()

    private suspend fun CreateNoteInteractor.latestNavigation() = navigation().first()
}
