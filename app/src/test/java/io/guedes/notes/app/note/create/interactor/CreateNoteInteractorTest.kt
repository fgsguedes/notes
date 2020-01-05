package io.guedes.notes.app.note.create.interactor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.guedes.notes.app.note.create.CreateNoteAction
import io.guedes.notes.app.note.create.CreateNoteResult
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteInteractorTest {

    private val dispatcher = TestCoroutineDispatcher()

    private val repository: NoteRepository = mock()
    private val interactor = CreateNoteInteractor(repository)


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

        // WHEN
        interactor.offer(CreateNoteAction.Init(note))

        // THEN
        assertThat(interactor.latestResults(2)).isEqualTo(
            listOf(
                CreateNoteResult.InitResult(noteId = 1L),
                CreateNoteResult.InputChanged(title = "Title", content = "Content")
            )
        )
    }

    @Test
    fun `on InputChanged action With valid data Should save note`() = runBlockingTest {
        // GIVEN
        val note = Note(id = 1L, title = "Title", content = "Content")

        // WHEN
        interactor.offer(CreateNoteAction.InputChanged(1L, "Title", "Content"))

        // THEN
        assertThat(interactor.latestResults(3)).isEqualTo(
            listOf(
                CreateNoteResult.InputChanged("Title", "Content"),
                CreateNoteResult.Validation(isValid = true),
                CreateNoteResult.NoteCreated
            )
        )
        verify(repository).save(note)
    }

    @Test
    fun `on InputChanged action With invalid data Should not save note`() = runBlockingTest {
        // WHEN
        interactor.offer(CreateNoteAction.InputChanged(0L, "", "Content"))

        // THEN
        assertThat(interactor.latestResults(2)).isEqualTo(
            listOf(
                CreateNoteResult.InputChanged("", "Content"),
                CreateNoteResult.Validation(isValid = false)
            )
        )
        verifyNoMoreInteractions(repository)
    }

    private suspend fun CreateNoteInteractor.latestResults(amount: Int) = results().take(amount).toList()
}
