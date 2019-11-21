package io.guedes.notes.domain.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.guedes.notes.domain.model.Note

@Dao
interface NoteDao {

    @Query("select * from Note order by id desc")
    fun list(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)
}
