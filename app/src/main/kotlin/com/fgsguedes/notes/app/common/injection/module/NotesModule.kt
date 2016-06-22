package com.fgsguedes.notes.app.common.injection.module

import com.fgsguedes.notes.app.note.presenter.CreateNotePresenter
import com.fgsguedes.notes.app.note.presenter.CreateNotePresenterImpl
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenter
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenterImpl
import com.fgsguedes.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotesModule {

  @Provides
  @Singleton
  fun provideListNotesPresenter(
      notesRepository: NoteRepository
  ): ListNotesPresenter {
    return ListNotesPresenterImpl(notesRepository)
  }

  @Provides
  @Singleton
  fun provideCreateNotePresenter(
      notesRepository: NoteRepository
  ): CreateNotePresenter {
    return CreateNotePresenterImpl(notesRepository)
  }
}
