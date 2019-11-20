package io.guedes.notes.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Label(
    val id: Long,
    val title: String
) : Parcelable
