package io.guedes.notes.app

import android.app.Application
import androidx.room.Room
import io.guedes.notes.domain.repository.AppDatabase
import io.guedes.notes.domain.repository.NoteRepository
import timber.log.Timber

class App : Application() {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "notes-database").build()
    }

    val noteRepository by lazy { NoteRepository(db.noteDao()) }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
