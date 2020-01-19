package io.guedes.notes.app.dependencies

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import io.guedes.notes.app.App
import io.guedes.notes.app.note.create.interactor.CreateNoteInteractor
import io.guedes.notes.app.note.create.ui.note
import io.guedes.notes.app.note.create.viewmodel.CreateNoteViewModelFactory
import io.guedes.notes.app.note.list.interactor.ListNotesInteractor
import io.guedes.notes.app.note.list.viewmodel.ListNotesViewModelFactory
import io.guedes.notes.domain.model.Note
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

val Context.noteRepository: NoteRepository
    get() = (applicationContext as App).noteRepository

private val defaultDispatcher = Dispatchers.Default

@FlowPreview
@ExperimentalCoroutinesApi
inline fun <reified T : ViewModelProvider.Factory> Context.provideFactory(
    intent: Intent? = null
): T = when (T::class) {
    ListNotesViewModelFactory::class -> listNotes(noteRepository) as T
    CreateNoteViewModelFactory::class -> createNote(noteRepository, intent?.note) as T
    else -> throw UnsupportedOperationException("Can't create instances of ${T::class.java.simpleName}")
}

@FlowPreview
@ExperimentalCoroutinesApi
fun listNotes(notesRepository: NoteRepository) = ListNotesViewModelFactory(
    lazy { ListNotesInteractor(notesRepository) }, defaultDispatcher
)

@FlowPreview
@ExperimentalCoroutinesApi
fun createNote(notesRepository: NoteRepository, note: Note?) = CreateNoteViewModelFactory(
    lazy { CreateNoteInteractor(notesRepository, note) }, defaultDispatcher
)
