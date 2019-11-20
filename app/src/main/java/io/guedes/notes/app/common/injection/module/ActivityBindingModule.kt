package io.guedes.notes.app.common.injection.module

import io.guedes.notes.app.note.ui.activity.CreateNoteActivity
import io.guedes.notes.app.note.ui.activity.ListNotesActivity
import io.guedes.notes.app.note.view.CreateNoteView
import io.guedes.notes.app.note.view.ListNotesView
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [CreateNoteModule::class])
    abstract fun createNoteActivity(): CreateNoteActivity

    @ContributesAndroidInjector(modules = [ListNotesModule::class])
    abstract fun listNotesActivity(): ListNotesActivity
}

@Module
abstract class CreateNoteModule {

    @Binds
    abstract fun bindActivity(activity: CreateNoteActivity): CreateNoteView
}

@Module
abstract class ListNotesModule {

    @Binds
    abstract fun bindActivity(activity: ListNotesActivity): ListNotesView
}
