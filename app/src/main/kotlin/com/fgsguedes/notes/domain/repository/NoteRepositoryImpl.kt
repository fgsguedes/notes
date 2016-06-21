package com.fgsguedes.notes.domain.repository

import com.fgsguedes.notes.domain.model.Note
import rx.Observable

class NoteRepositoryImpl() : NoteRepository {
  val KEY_NOTES_LIST = "notesList"

  override fun list() = Observable.empty<List<Note>>()

  override fun create(title: String, content: String?): Observable<Note> {

    return Observable.just(Note(1L, title, content))
  }
}
