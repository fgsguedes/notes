package io.guedes.notes.domain.repository

import io.guedes.notes.domain.model.Note

object NoteRepository {

    suspend fun list(): List<Note> = TODO()

    suspend fun create(
        title: String,
        content: String?
    ): Note = TODO()
}
