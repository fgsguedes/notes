package io.guedes.notes.domain.repository

import androidx.room.Dao
import io.guedes.notes.domain.model.Note

@Dao
interface NoteDao {

    suspend fun list(): List<Note>
    suspend fun insert(note: Note)
}
