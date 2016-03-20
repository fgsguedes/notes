package br.com.hardcoded.notes.app.injection.component

import br.com.hardcoded.notes.app.injection.module.ListNotesModule
import br.com.hardcoded.notes.app.listnotes.ui.activity.ListNotesActivity
import dagger.Component

@Component(
    modules = arrayOf(
        ListNotesModule::class
    )
)
interface ListNotesComponent {
  fun inject(listNotesActivity: ListNotesActivity)
}
