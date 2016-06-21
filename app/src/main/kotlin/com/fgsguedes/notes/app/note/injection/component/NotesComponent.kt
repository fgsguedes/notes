package com.fgsguedes.notes.app.note.injection.component

import com.fgsguedes.notes.app.common.injection.PerActivity
import com.fgsguedes.notes.app.common.injection.component.ApplicationComponent
import com.fgsguedes.notes.app.note.injection.module.NotesModule
import com.fgsguedes.notes.app.note.ui.activity.CreateNoteActivity
import com.fgsguedes.notes.app.note.ui.activity.ListNotesActivity
import dagger.Component

@PerActivity
@Component(
    dependencies = arrayOf(
        ApplicationComponent::class
    ),
    modules = arrayOf(
        NotesModule::class
    )
)
interface NotesComponent {
  fun inject(listNotesActivity: ListNotesActivity)
  fun inject(createNotesActivity: CreateNoteActivity)
}
