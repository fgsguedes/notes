package io.guedes.notes.app.note.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.guedes.notes.domain.repository.NoteRepository
import kotlinx.coroutines.launch

class CreateNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val result = MutableLiveData<Result>()

    fun results(): LiveData<Result> = result

    fun doneClicked(title: String, content: String) {

        if (title.isNotBlank() && content.isNotBlank()) {
            result.postValue(Result.INVALID)

        } else {
            viewModelScope.launch {
                noteRepository.create(title, content)
                result.postValue(Result.CREATED)
            }
        }
    }

    enum class Result {
        CREATED, INVALID
    }

}

