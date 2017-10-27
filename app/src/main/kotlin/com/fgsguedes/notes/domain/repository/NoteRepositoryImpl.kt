package com.fgsguedes.notes.domain.repository

import com.fgsguedes.notes.domain.model.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Single

class NoteRepositoryImpl(
    private val databaseReference: DatabaseReference
) : NoteRepository {

  override fun list(): Observable<Note> = Observable.create<Note> { emitter ->
    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {

      override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children
            .map { it.getValue(Note::class.java) }
            .forEach {
              if (it != null) emitter.onNext(it)
            }

        emitter.onComplete()
      }

      override fun onCancelled(error: DatabaseError) {
        emitter.onError(error.toException())
      }
    })
  }

  override fun create(
      title: String,
      content: String?
  ): Single<Note> = Single.fromCallable {
    Note(title, content).apply {
      val key = databaseReference.push().key
      databaseReference.child(key).setValue(this)
    }
  }
}
