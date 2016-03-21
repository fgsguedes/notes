package br.com.hardcoded.notes.app.injection.component

import android.content.Context
import br.com.hardcoded.notes.app.injection.module.AndroidModule
import br.com.hardcoded.notes.app.injection.module.RepositoryModule
import br.com.hardcoded.notes.domain.repository.NoteRepository
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
