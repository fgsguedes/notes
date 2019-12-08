package io.guedes.notes.domain.repository

import io.guedes.notes.domain.model.Note

class NoteRepository(
    private val noteDao: NoteDao
) {
    suspend fun list() = noteDao.list()
    suspend fun save(note: Note) = noteDao.save(note)
    suspend fun delete(noteId: Long) = noteDao.delete(noteId)
    suspend fun restore(noteId: Long) = noteDao.restore(noteId)
}
