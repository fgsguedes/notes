package com.fgsguedes.notes.domain.usecase

import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.repository.NoteRepository
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
