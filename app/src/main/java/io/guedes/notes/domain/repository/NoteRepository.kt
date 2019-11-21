package io.guedes.notes.domain.repository

import io.guedes.notes.domain.model.Note

class NoteRepository(
    private val noteDao: NoteDao
) {

    fun list() = noteDao.list()

    suspend fun create(title: String, content: String?) =
        noteDao.insert(Note(title, content))
}
