package io.guedes.notes.domain.repository

import io.guedes.notes.domain.model.Note

class NoteRepository {

    fun list(): List<Note> = TODO()

    fun create(
        title: String,
        content: String?
    ): Note = TODO()
}
