package com.fgsguedes.notes.app.common.injection.module

import com.fgsguedes.notes.app.note.ui.activity.CreateNoteActivity
import com.fgsguedes.notes.app.note.ui.activity.ListNotesActivity
import com.fgsguedes.notes.app.note.view.CreateNoteView
import com.fgsguedes.notes.app.note.view.ListNotesView
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
