package io.guedes.notes.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Note(
    val id: Long,
    val title: String,
    val content: String? = null,
    val label: Label? = null,
    val remindOn: Date? = null
) : Parcelable
