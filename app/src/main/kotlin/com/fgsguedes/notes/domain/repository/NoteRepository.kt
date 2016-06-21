package com.fgsguedes.notes.domain.repository

import com.fgsguedes.notes.domain.model.Note
import rx.Observable

interface NoteRepository {
  fun list(): Observable<Note>
  fun create(title: String, content: String?): Observable<Note>
}
