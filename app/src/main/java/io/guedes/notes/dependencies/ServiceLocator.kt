package io.guedes.notes.dependencies

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import io.guedes.notes.app.App
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModelFactory
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModelFactory
import io.guedes.notes.domain.repository.NoteRepository

val Context.noteRepository: NoteRepository
    get() = (applicationContext as App).noteRepository

inline fun <reified T : ViewModelProvider.Factory> Context.provideFactory(): T =
    when (T::class) {
        ListNotesViewModelFactory::class -> ListNotesViewModelFactory(noteRepository) as T
        CreateNoteViewModelFactory::class -> CreateNoteViewModelFactory(noteRepository) as T
        else -> throw UnsupportedOperationException("Can't create instances of ${T::class.java.simpleName}")
    }
