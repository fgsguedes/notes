package io.guedes.notes.domain.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.guedes.notes.domain.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("select * from Note where deleted = 0")
    suspend fun list(): List<Note>

    @Query("update Note set deleted = 1 where id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("update Note set deleted = 0 where id = :noteId")
    suspend fun restore(noteId: Long)
}
