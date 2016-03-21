package br.com.hardcoded.notes.data.repository

import br.com.hardcoded.notes.domain.model.Label
import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.repository.NoteRepository
import rx.Observable

class NoteRepositoryImpl : NoteRepository {

  val dummyNote = Note(1, "Dummy note", "Soon this will be big", Label(1, "Hello world"))

  override fun list() = Observable.just(listOf(dummyNote))
}
