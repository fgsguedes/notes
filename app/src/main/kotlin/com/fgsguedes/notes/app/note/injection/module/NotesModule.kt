package com.fgsguedes.notes.app.note.injection.module

import com.fgsguedes.notes.app.common.injection.PerActivity
import com.fgsguedes.notes.app.note.presenter.CreateNotePresenter
import com.fgsguedes.notes.app.note.presenter.CreateNotePresenterImpl
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenter
import com.fgsguedes.notes.app.note.presenter.ListNotesPresenterImpl
import com.fgsguedes.notes.domain.repository.NoteRepository
import com.fgsguedes.notes.domain.usecase.CreateNoteUseCase
import com.fgsguedes.notes.domain.usecase.GetNoteListUseCase
import dagger.Module
import dagger.Provides

@Module
@PerActivity
class NotesModule {

  @Provides
  fun provideListNotesPresenter(
      getNoteListUseCase: GetNoteListUseCase
  ): ListNotesPresenter {
    return ListNotesPresenterImpl(getNoteListUseCase)
  }

  @Provides
  fun provideCreateNotePresenter(
      createNoteUseCase: CreateNoteUseCase
  ): CreateNotePresenter {
    return CreateNotePresenterImpl(createNoteUseCase)
  }

  @Provides
  fun provideListNotesUseCase(noteRepository: NoteRepository) = GetNoteListUseCase(noteRepository)

  @Provides
  fun providePutNoteUseCase(noteRepository: NoteRepository) = CreateNoteUseCase(noteRepository)
}
