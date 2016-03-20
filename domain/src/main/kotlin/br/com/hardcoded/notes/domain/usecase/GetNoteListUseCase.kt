package br.com.hardcoded.notes.domain.usecase

import br.com.hardcoded.notes.domain.model.Label
import br.com.hardcoded.notes.domain.model.Note
import rx.Observable
import javax.inject.Singleton

@Singleton
class GetNoteListUseCase : UseCase<Array<Note>>() {

  val dummyNote = Note(1, "Dummy note", "Soon this will be big", Label(1, "Hello world"))

  override fun buildObservable() = Observable.just(arrayOf(dummyNote))
}
