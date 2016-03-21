package br.com.hardcoded.notes.app.injection.module

import br.com.hardcoded.notes.data.repository.NoteRepositoryImpl
import br.com.hardcoded.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class RepositoryModule {

  @Provides
  fun provideNoteRepository(): NoteRepository = NoteRepositoryImpl()
}
