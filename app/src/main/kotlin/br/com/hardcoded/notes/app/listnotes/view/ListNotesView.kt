package br.com.hardcoded.notes.app.listnotes.view

import br.com.hardcoded.notes.domain.model.Note

interface ListNotesView {
  fun showNotes(notes: List<Note>)
}
