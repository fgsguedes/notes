package br.com.hardcoded.notes.app.injection.module

import android.content.SharedPreferences
import br.com.hardcoded.notes.data.repository.Json
import br.com.hardcoded.notes.data.repository.NoteRepositoryImpl
import br.com.hardcoded.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class RepositoryModule {

  @Provides
  fun provideNoteRepository(
      sharedPreferences: SharedPreferences,
      json: Json
  ): NoteRepository {
    return NoteRepositoryImpl(sharedPreferences, json)
  }
}
