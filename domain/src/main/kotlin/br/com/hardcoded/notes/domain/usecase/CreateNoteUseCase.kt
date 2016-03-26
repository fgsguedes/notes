package br.com.hardcoded.notes.domain.usecase

import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.repository.NoteRepository
import rx.Subscriber

class CreateNoteUseCase(
    private val noteRepository: NoteRepository
): UseCase<Unit>() {

  lateinit var note: Note

  fun subscribe(note: Note, subscriber: Subscriber<Unit>) {
    this.note = note
    internalSubscribe(subscriber)
  }

  override fun buildObservable() = noteRepository.create(note)
}
