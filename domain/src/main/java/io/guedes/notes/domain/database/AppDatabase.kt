package io.guedes.notes.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.guedes.notes.domain.dao.NoteDao
import io.guedes.notes.domain.model.Note
import java.util.*

@TypeConverters(DateConverter::class)
@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? = date?.time
}
