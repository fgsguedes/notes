package com.fgsguedes.notes.domain.usecase

import com.fgsguedes.notes.domain.model.Note
import com.fgsguedes.notes.domain.repository.NoteRepository
import rx.Subscriber
import javax.inject.Singleton

@Singleton
class GetNoteListUseCase(
    private val noteRepository: NoteRepository
) : UseCase<List<Note>>() {

  fun subscribe(subscriber: Subscriber<List<Note>>) {
    internalSubscribe(subscriber)
  }

  override fun buildObservable() = noteRepository.list()
}
