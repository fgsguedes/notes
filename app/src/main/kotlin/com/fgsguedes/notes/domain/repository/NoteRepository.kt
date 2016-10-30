package com.fgsguedes.notes.domain.repository

import com.fgsguedes.notes.domain.model.Note
import io.reactivex.Observable
import io.reactivex.Single

interface NoteRepository {
  fun list(): Observable<Note>
  fun create(title: String, content: String?): Single<Note>
}
