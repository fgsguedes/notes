package com.fgsguedes.notes.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@SuppressLint("ParcelCreator")
data class Note(
    val title: String,
    val content: String? = null,
    val label: Label? = null,
    val remindOn: Date? = null
) : Parcelable {
  private constructor() : this("", "") // For Firebase
}
