package com.fgsguedes.notes.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@SuppressLint("ParcelCreator")
data class Label(
    val id: Long,
    val title: String
) : Parcelable
