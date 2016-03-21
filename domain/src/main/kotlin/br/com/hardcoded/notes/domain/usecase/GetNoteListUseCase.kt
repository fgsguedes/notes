package br.com.hardcoded.notes.domain.usecase

import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.repository.NoteRepository
import javax.inject.Singleton

@Singleton
class GetNoteListUseCase(
    private val noteRepository: NoteRepository
) : UseCase<List<Note>>() {

  override fun buildObservable() = noteRepository.list()
}
