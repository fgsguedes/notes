package br.com.hardcoded.notes.data.repository

import android.content.SharedPreferences
import br.com.hardcoded.notes.domain.model.Note
import br.com.hardcoded.notes.domain.repository.NoteRepository
import rx.Observable

class NoteRepositoryImpl(
    private val preferences: SharedPreferences,
    private val json: Json
) : NoteRepository {
  val KEY_NOTES_LIST = "notesList"

  val lock = Any()

  override fun list() = synchronized(lock) {
    Observable.just(notes().toList())
  }

  override fun create(title: String, content: String?) = synchronized(lock) {
    val note = Note(nextId(), title, content)

    json.format(arrayOf(*notes(), note)).let { resultingJson ->
      preferences.edit()
          .putString(KEY_NOTES_LIST, resultingJson)
          .apply()
    }

    Observable.just(note)
  }

  private fun notes() = synchronized(lock) {
    preferences.getString(KEY_NOTES_LIST, "[]").let {
      json.parse(it, Array<Note>::class)
    }
  }

  private fun nextId() = synchronized(lock) {
    (notes().map { it.id }.max() ?: 0) + 1
  }
}
