package br.com.hardcoded.notes.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Label(
    val id: Long,
    val title: String
) : Parcelable {

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeLong(id)
    dest.writeString(title)
  }

  override fun describeContents() = 0

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<Label> {
      override fun newArray(size: Int) = emptyArray<Label>()

      override fun createFromParcel(source: Parcel) = Label(
          source.readLong(), source.readString()
      )
    }
  }
}

