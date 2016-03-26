package br.com.hardcoded.notes.app.note.injection.component

import br.com.hardcoded.notes.app.common.injection.PerActivity
import br.com.hardcoded.notes.app.common.injection.component.ApplicationComponent
import br.com.hardcoded.notes.app.note.injection.module.NotesModule
import br.com.hardcoded.notes.app.note.ui.activity.ListNotesActivity
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
}
