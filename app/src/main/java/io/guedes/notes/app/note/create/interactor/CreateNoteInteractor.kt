package io.guedes.notes.app.note.create.interactor

import io.guedes.notes.arch.BaseInteractor
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import io.guedes.notes.app.note.create.CreateNoteAction as Action
import io.guedes.notes.app.note.create.CreateNoteNavigation as Navigation
import io.guedes.notes.app.note.create.CreateNoteResult as Result

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteInteractor(
    private val noteRepository: NoteRepository,
    private val note: Note?
) : BaseInteractor<Action, Result, Navigation>() {

    override fun process(action: Action) =
        when (action) {
            Action.Init -> onInitAction()
            is Action.SaveNote -> onInputChangedAction(action.title, action.content)
        }

    private fun onInitAction() =
        if (note == null) emptyFlow()
        else flowOf(Result.InputChanged(note.title, note.content))

    private fun onInputChangedAction(title: String, content: String) = flow {
        val isValid = title.isNotBlank()
        val formattedContent = content.takeIf { it.isNotBlank() }

        emit(Result.InputChanged(title, formattedContent))
        emit(Result.Validation(isValid = isValid))

        if (isValid) {
            val note = Note(id = note?.id ?: 0, title = title, content = formattedContent)
            noteRepository.save(note)
            offer(Navigation.Finish)
        }
    }
}
