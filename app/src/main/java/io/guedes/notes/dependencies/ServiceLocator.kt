package io.guedes.notes.dependencies

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import io.guedes.notes.app.App
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModelFactory
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModelFactory
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

val Context.noteRepository: NoteRepository
    get() = (applicationContext as App).noteRepository

@FlowPreview
@ExperimentalCoroutinesApi
inline fun <reified T : ViewModelProvider.Factory> Context.provideFactory(
    intent: Intent? = null
): T = when (T::class) {
    ListNotesViewModelFactory::class -> ListNotesViewModelFactory(noteRepository) as T
    CreateNoteViewModelFactory::class -> CreateNoteViewModelFactory(intent, noteRepository) as T
    else -> throw UnsupportedOperationException("Can't create instances of ${T::class.java.simpleName}")
}
