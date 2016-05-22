package br.com.hardcoded.notes.data.repository

import android.content.ContentValues
import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.repository.NoteRepository
import justmakeitwork.DatabaseHelper
import rx.Observable

class NoteRepositoryImpl(
    private val databaseHelper: DatabaseHelper
) : NoteRepository {

  val lock = Any()

  override fun list() = synchronized(lock) {
    Observable.just(notes())
  }

  override fun create(title: String, content: String?): Observable<Note> {
    val database = databaseHelper.writableDatabase
    val transaction = database.beginTransaction()

    val contentValues = ContentValues(2).apply {
      put("title", title)
      put("content", content)
    }

    val id = database.insert("Note", null, contentValues)
    database.setTransactionSuccessful()
    database.endTransaction()
    database.close()

    return Observable.just(Note(id, title, content))
  }

  private fun notes(): List<Note> = synchronized(lock) {
    val database = databaseHelper.readableDatabase

    val cursor = database.query("Note",
        arrayOf("id", "title", "content"),
        null, null, null, null, "id desc", null)

    val notes = mutableListOf<Note>()

    while (cursor.moveToNext()) {
      notes.add(Note(
          cursor.getLong(cursor.getColumnIndex("id")),
          cursor.getString(cursor.getColumnIndex("title")),
          cursor.getString(cursor.getColumnIndex("content"))
      ))
    }

    cursor.close()

    return notes.toList()
  }
}
