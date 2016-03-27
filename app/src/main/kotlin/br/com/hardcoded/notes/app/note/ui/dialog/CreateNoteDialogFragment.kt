package br.com.hardcoded.notes.app.note.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.EditText
import br.com.hardcoded.notes.R
import br.com.hardcoded.notes.app.common.stringText
import br.com.hardcoded.notes.domain.model.Note

class CreateNoteDialogFragment : DialogFragment() {

  val titleEditText by lazy { dialog.findViewById(R.id.edit_text_note_title) as EditText }
  val contentEditText by lazy { dialog.findViewById(R.id.edit_text_note_content) as EditText }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

    return AlertDialog.Builder(activity)
        .setTitle(R.string.new_note_dialog_title)
        .setView(R.layout.dialog_create_note)
        .setPositiveButton(R.string.save) { dialogInterface, which ->
          Log.e(TAG, Note(1L, titleEditText.stringText, contentEditText.stringText).toString())
        }
        .create()
  }

  companion object {
    val TAG = CreateNoteDialogFragment::class.simpleName
  }
}
