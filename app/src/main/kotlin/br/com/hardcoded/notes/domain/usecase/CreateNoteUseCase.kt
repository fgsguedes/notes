package br.com.hardcoded.notes.domain.usecase

import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.repository.NoteRepository
import rx.Subscriber

class CreateNoteUseCase(
    private val noteRepository: NoteRepository
): UseCase<Note>() {

  lateinit var title: String
  var content: String? = null

  fun subscribe(title: String, content: String?, subscriber: Subscriber<Note>) {
    this.title = title
    this.content = content
    internalSubscribe(subscriber)
  }

  override fun buildObservable() = noteRepository.create(title, content)
}
