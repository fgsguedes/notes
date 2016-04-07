package br.com.hardcoded.notes.app.note.injection.module

import br.com.hardcoded.notes.app.common.injection.PerActivity
import br.com.hardcoded.notes.app.note.presenter.CreateNotePresenter
import br.com.hardcoded.notes.app.note.presenter.CreateNotePresenterImpl
import br.com.hardcoded.notes.app.note.presenter.ListNotesPresenter
import br.com.hardcoded.notes.app.note.presenter.ListNotesPresenterImpl
import br.com.hardcoded.notes.domain.repository.NoteRepository
import br.com.hardcoded.notes.domain.usecase.CreateNoteUseCase
import br.com.hardcoded.notes.domain.usecase.GetNoteListUseCase
import dagger.Module
import dagger.Provides

@Module
@PerActivity
class NotesModule {

  @Provides
  fun provideListNotesPresenter(
      getNoteListUseCase: GetNoteListUseCase,
      createNoteUseCase: CreateNoteUseCase
  ): ListNotesPresenter {
    return ListNotesPresenterImpl(getNoteListUseCase, createNoteUseCase)
  }

  @Provides
  fun provideCreateNotePresenter(): CreateNotePresenter = CreateNotePresenterImpl()

  @Provides
  fun provideListNotesUseCase(noteRepository: NoteRepository) = GetNoteListUseCase(noteRepository)

  @Provides
  fun providePutNoteUseCase(noteRepository: NoteRepository) = CreateNoteUseCase(noteRepository)
}
