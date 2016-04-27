package br.com.hardcoded.notes.domain.repository

import br.com.hardcoded.notes.domain.model.Note
import rx.Observable

interface NoteRepository {
  fun list(): Observable<List<Note>>
  fun create(note: Note): Observable<Unit>
}