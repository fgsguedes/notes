package com.fgsguedes.notes.app.common.injection.component

import com.fgsguedes.notes.app.common.injection.module.NotesModule
import com.fgsguedes.notes.app.common.injection.module.RepositoryModule
import com.fgsguedes.notes.app.note.ui.activity.CreateNoteActivity
import com.fgsguedes.notes.app.note.ui.activity.ListNotesActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        RepositoryModule::class,
        NotesModule::class
    )
)
interface ApplicationComponent {
  fun inject(createNoteActivity: CreateNoteActivity)
  fun inject(listNotesActivity: ListNotesActivity)
}
