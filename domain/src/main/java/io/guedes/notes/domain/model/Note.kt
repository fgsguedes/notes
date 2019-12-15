package io.guedes.notes.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Note(
    val title: String,
    val content: String? = null,
    val remindOn: Date? = null,
    val deleted: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) : Parcelable
