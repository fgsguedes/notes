package com.fgsguedes.notes.app.common.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class FirebaseModule {

  @Provides
  @Singleton
  fun provideFirebaseAuth() = FirebaseAuth.getInstance()

  @Provides
  @Singleton
  fun provideFirebaseDatabase() = FirebaseDatabase.getInstance().apply {
    setPersistenceEnabled(true)
  }

  @Provides
  @Singleton
  @Named("notes")
  fun provideNoteDatabaseReference(
      firebaseDatabase: FirebaseDatabase
  ): DatabaseReference {
    return firebaseDatabase.getReference("notes")
  }
}
