package br.com.hardcoded.notes.app.injection.module

import br.com.hardcoded.notes.app.injection.PerActivity
import br.com.hardcoded.notes.app.listnotes.presenter.ListNotesPresenter
import br.com.hardcoded.notes.app.listnotes.presenter.ListNotesPresenterImpl
import br.com.hardcoded.notes.domain.usecase.GetNoteListUseCase
import dagger.Module
import dagger.Provides

@Module
@PerActivity
class ListNotesModule {

  @Provides
  fun provideListNotesPresenter(getNoteListUseCase: GetNoteListUseCase): ListNotesPresenter {
    return ListNotesPresenterImpl(getNoteListUseCase)
  }

  @Provides
  fun provideListNotesUseCase() = GetNoteListUseCase()
}
