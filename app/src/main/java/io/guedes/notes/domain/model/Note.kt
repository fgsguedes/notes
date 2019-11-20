package io.guedes.notes.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val title: String,
    val content: String? = null,
    val label: Label? = null,
    val remindOn: Date? = null
) : Parcelable
