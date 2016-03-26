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

  override fun list() = Observable.just(notes().toList())

  override fun create(note: Note) = Observable.create<Unit> { subscriber ->
    json.format(arrayOf(*notes(), note)).let { resultingJson ->
      preferences.edit()
          .putString(KEY_NOTES_LIST, resultingJson)
          .apply { subscriber.onCompleted() }
    }
  }

  private fun notes() = preferences.getString(KEY_NOTES_LIST, "").let {
    json.parse(it, Array<Note>::class.java)
  }
}
