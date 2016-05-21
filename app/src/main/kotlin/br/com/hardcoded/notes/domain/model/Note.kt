package br.com.hardcoded.notes.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Note(
    val id: Long,
    val title: String,
    val content: String? = null,
    val label: Label? = null,
    val remindOn: Date? = null
) : Parcelable {

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.apply {
      writeLong(id)
      writeString(title)
      writeString(content)
      writeParcelable(label, 0)
      writeValue(remindOn?.time)
    }
  }

  override fun describeContents() = 0

  companion object {
    @JvmField val CREATOR = object : Parcelable.Creator<Note> {
      override fun newArray(size: Int) = emptyArray<Note>()

      override fun createFromParcel(source: Parcel) = Note(
          source.readLong(),
          source.readString(),
          source.readString(),
          source.readParcelable(Label::class.java.classLoader),
          source.readValue(Long::class.java.classLoader)?.let {
            Date(it as Long)
          }
      )
    }
  }
}
