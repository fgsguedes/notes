package com.fgsguedes.notes.app.common.injection.component

import android.content.Context
import com.fgsguedes.notes.app.common.injection.module.AndroidModule
import com.fgsguedes.notes.app.common.injection.module.RepositoryModule
import com.fgsguedes.notes.domain.repository.NoteRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidModule::class,
        RepositoryModule::class
    )
)
interface ApplicationComponent {

  // Android
  fun context(): Context

  // Repositories
  fun noteRepository(): NoteRepository
}
