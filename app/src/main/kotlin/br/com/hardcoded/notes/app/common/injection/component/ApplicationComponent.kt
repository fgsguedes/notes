package br.com.hardcoded.notes.app.common.injection.component

import android.content.Context
import br.com.hardcoded.notes.app.common.injection.module.AndroidModule
import br.com.hardcoded.notes.app.common.injection.module.RepositoryModule
import br.com.hardcoded.notes.app.common.injection.module.SQLiteModule
import br.com.hardcoded.notes.domain.repository.NoteRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidModule::class,
        SQLiteModule::class,
        RepositoryModule::class
    )
)
interface ApplicationComponent {

  // Android
  fun context(): Context

  // Repositories
  fun noteRepository(): NoteRepository
}
