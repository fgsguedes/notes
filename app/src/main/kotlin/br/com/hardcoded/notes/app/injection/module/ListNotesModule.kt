package br.com.hardcoded.notes.app.injection.module

import br.com.hardcoded.notes.app.injection.PerActivity
import br.com.hardcoded.notes.app.listnotes.presenter.ListNotesPresenter
import br.com.hardcoded.notes.app.listnotes.presenter.ListNotesPresenterImpl
import br.com.hardcoded.notes.domain.repository.NoteRepository
import br.com.hardcoded.notes.domain.usecase.GetNoteListUseCase
import br.com.hardcoded.notes.domain.usecase.CreateNoteUseCase
import dagger.Module
import dagger.Provides

@Module
@PerActivity
class ListNotesModule {

  @Provides
  fun provideListNotesPresenter(
      getNoteListUseCase: GetNoteListUseCase,
      createNoteUseCase: CreateNoteUseCase
  ): ListNotesPresenter {
    return ListNotesPresenterImpl(getNoteListUseCase, createNoteUseCase)
  }

  @Provides
  fun provideListNotesUseCase(noteRepository: NoteRepository) = GetNoteListUseCase(noteRepository)

  @Provides
  fun providePutNoteUseCase(noteRepository: NoteRepository) = CreateNoteUseCase(noteRepository)
}
