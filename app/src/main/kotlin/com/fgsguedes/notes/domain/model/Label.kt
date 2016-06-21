package com.fgsguedes.notes.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Label(
    val id: Long,
    val title: String
) : Parcelable {

  constructor(source: Parcel) : this(
      source.readLong(),
      source.readString()
  )

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.apply {
      writeLong(id)
      writeString(title)
    }
  }

  override fun describeContents() = 0

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<Label> {
      override fun createFromParcel(source: Parcel) = Label(source)
      override fun newArray(size: Int) = arrayOfNulls<Label>(size)
    }

  }
}

