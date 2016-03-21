package br.com.hardcoded.notes.app.injection.component

import br.com.hardcoded.notes.app.injection.PerActivity
import br.com.hardcoded.notes.app.injection.module.ListNotesModule
import br.com.hardcoded.notes.app.listnotes.ui.activity.ListNotesActivity
import dagger.Component

@PerActivity
@Component(
    dependencies = arrayOf(
        ApplicationComponent::class
    ),
    modules = arrayOf(
        ListNotesModule::class
    )
)
interface ListNotesComponent {
  fun inject(listNotesActivity: ListNotesActivity)
}
