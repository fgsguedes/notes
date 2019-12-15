package io.guedes.notes.app.note.create.interactor

import io.guedes.notes.arch.BaseInteractor
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import io.guedes.notes.app.note.create.CreateNoteAction as Action
import io.guedes.notes.app.note.create.CreateNoteResult as Result

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteInteractor(
    private val noteRepository: NoteRepository
) : BaseInteractor<Action, Result>() {

    override fun process(action: Action) =
        when (action) {
            is Action.Init -> onInitAction(action.note)
            is Action.InputChanged ->
                onInputChangedAction(action.noteId, action.title, action.content)
        }

    private fun onInitAction(note: Note?) =
        if (note == null) emptyFlow()
        else flow {
            emit(Result.InitResult(note.id))
            emit(Result.InputChanged(note.title, note.content))
        }

    private fun onInputChangedAction(noteId: Long, title: String, content: String) = flow {
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
}
