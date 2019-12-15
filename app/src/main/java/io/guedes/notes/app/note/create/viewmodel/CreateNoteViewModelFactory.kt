package io.guedes.notes.app.note.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.guedes.notes.app.note.create.interactor.CreateNoteInteractor
import io.guedes.notes.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class CreateNoteViewModelFactory(
    private val provider: Lazy<CreateNoteInteractor>,
    private val note: Note?
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CreateNoteViewModel(provider.value, note) as T
}
