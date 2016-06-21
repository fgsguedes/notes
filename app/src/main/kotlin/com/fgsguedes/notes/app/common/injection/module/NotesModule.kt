package com.fgsguedes.notes.app.common.injection.module

import com.fgsguedes.notes.app.note.presenter.CreateNotePresenter
import com.fgsguedes.notes.app.note.presenter.CreateNotePresenterImpl
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenter
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenterImpl
import com.fgsguedes.notes.domain.repository.NoteRepository
import com.fgsguedes.notes.domain.usecase.CreateNoteUseCase
import com.fgsguedes.notes.domain.usecase.GetNoteListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotesModule {

  @Provides
  @Singleton
  fun provideListNotesPresenter(
      getNoteListUseCase: GetNoteListUseCase
  ): ListNotesPresenter {
    return ListNotesPresenterImpl(getNoteListUseCase)
  }

  @Provides
  @Singleton
  fun provideCreateNotePresenter(
      createNoteUseCase: CreateNoteUseCase
  ): CreateNotePresenter {
    return CreateNotePresenterImpl(createNoteUseCase)
  }

  @Provides
  @Singleton
  fun provideListNotesUseCase(noteRepository: NoteRepository) = GetNoteListUseCase(noteRepository)

  @Provides
  @Singleton
  fun providePutNoteUseCase(noteRepository: NoteRepository) = CreateNoteUseCase(noteRepository)
}
