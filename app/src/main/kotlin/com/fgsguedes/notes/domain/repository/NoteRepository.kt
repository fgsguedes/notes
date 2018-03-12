package com.fgsguedes.notes.domain.repository

import com.fgsguedes.notes.domain.model.Note
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val database: DatabaseReference
) {

    fun list(): Observable<Note> = Observable.create<Note> { emitter ->
        val listener = ValueListener(emitter)
        database.addListenerForSingleValueEvent(listener)
    }

    fun create(
        title: String,
        content: String?
    ): Single<Note> = Single.fromCallable {
        Note(title, content).apply {
            val key = database.push().key
            database.child(key).setValue(this)
        }
    }
}

private class ValueListener(
    val emitter: ObservableEmitter<Note>
) : ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.children.map(::toNote)
            .forEach(::emmit)

        emitter.onComplete()
    }

    override fun onCancelled(error: DatabaseError) {
        emitter.onError(error.toException())
    }

    fun toNote(snapshot: DataSnapshot) =
        snapshot.getValue(Note::class.java)

    fun emmit(note: Note?) {
        if (note != null) emitter.onNext(note)
    }
}
