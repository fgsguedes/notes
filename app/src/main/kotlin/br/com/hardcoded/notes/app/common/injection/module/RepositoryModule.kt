package br.com.hardcoded.notes.app.common.injection.module

import br.com.hardcoded.notes.data.repository.NoteRepositoryImpl
import br.com.hardcoded.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import justmakeitwork.DatabaseHelper
import javax.inject.Singleton

@Module
@Singleton
class RepositoryModule {

  @Provides
  fun provideNoteRepository(
      databaseHelper: DatabaseHelper
  ): NoteRepository {
    return NoteRepositoryImpl(databaseHelper)
  }
}
