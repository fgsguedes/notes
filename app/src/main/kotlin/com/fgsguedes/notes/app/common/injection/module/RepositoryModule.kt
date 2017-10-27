package com.fgsguedes.notes.app.common.injection.module

import com.fgsguedes.notes.domain.repository.NoteRepository
import com.fgsguedes.notes.domain.repository.NoteRepositoryImpl
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun provideNoteRepository(
      @Named("notes") databaseReference: DatabaseReference
  ): NoteRepository = NoteRepositoryImpl(databaseReference)
}
