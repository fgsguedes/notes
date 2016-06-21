package com.fgsguedes.notes.app.common.injection.module

import com.fgsguedes.notes.domain.repository.NoteRepositoryImpl
import com.fgsguedes.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class RepositoryModule {

  @Provides
  fun provideNoteRepository(): NoteRepository {
    return NoteRepositoryImpl()
  }
}
