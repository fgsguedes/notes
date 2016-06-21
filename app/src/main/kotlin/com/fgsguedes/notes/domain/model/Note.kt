package com.fgsguedes.notes.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Note(
    val title: String,
    val content: String? = null,
    val label: Label? = null,
    val remindOn: Date? = null
) : Parcelable {

  private constructor() : this("", "") // For Firebase

  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readParcelable(Label::class.java.classLoader),
      source.readLong().let {
        when (it) {
          Long.MIN_VALUE -> null
          else -> Date(it)
        }
      }
  )

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.apply {
      writeString(title)
      writeString(content)
      writeParcelable(label, 0)
      writeLong(remindOn?.time ?: Long.MIN_VALUE)
    }
  }

  override fun describeContents() = 0

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<Note> {
      override fun createFromParcel(source: Parcel) = Note(source)
      override fun newArray(size: Int) = arrayOfNulls<Note>(size)
    }
  }
}
