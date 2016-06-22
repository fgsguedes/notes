package com.fgsguedes.notes.domain.repository

import com.fgsguedes.notes.domain.model.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import rx.Observable

class NoteRepositoryImpl(
    val databaseReference: DatabaseReference
) : NoteRepository {

  override fun list(): Observable<Note> {
    return Observable.create { subscriber ->
      databaseReference
          .addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
              snapshot.children
                  .map { it.getValue(Note::class.java) }
                  .forEach { subscriber.onNext(it) }

              subscriber.onCompleted()
            }

            override fun onCancelled(error: DatabaseError) {
              subscriber.onError(error.toException())
            }
          })
    }
  }

  override fun create(title: String, content: String?): Observable<Note> {
    return Observable.fromCallable {
      Note(title, content).apply {
        val key = databaseReference.push().key
        databaseReference.child(key).setValue(this)
      }
    }
  }
}
