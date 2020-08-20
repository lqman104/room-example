package com.luqman.roomexercize.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.luqman.roomexercize.R
import com.luqman.roomexercize.repository.NoteDataSource
import com.luqman.roomexercize.repository.NoteRepository
import com.luqman.roomexercize.repository.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class NoteFormDialogFragment : DialogFragment() {

    private var textInputLayoutNote: TextInputLayout? = null
    private var textInputEditTextNote: TextInputEditText? = null
    private var buttonSave: Button? = null
    private var buttonCancel: Button? = null

    private var repository: NoteDataSource? = null
    private var callback: NoteCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = when {
            context is NoteCallback -> context
            parentFragment is NoteCallback || activity is NoteCallback -> context as NoteCallback
            else -> throw Exception("$parentFragment or $activity must implement NoteCallback")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.form_note_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListener()
    }

    private fun init(view: View) {
        textInputLayoutNote = view.findViewById(R.id.text_input_layout_note)
        textInputEditTextNote = view.findViewById(R.id.text_input_edit_text_note)
        buttonSave = view.findViewById(R.id.button_save)
        buttonCancel = view.findViewById(R.id.button_cancel)

        repository = NoteRepository()
    }

    private fun initListener() {
        buttonCancel?.setOnClickListener {
            dismiss()
        }

        buttonSave?.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        CoroutineScope(Main).launch {
            val note = textInputEditTextNote?.text?.toString()
            val request = note?.let { Note(note = it) }

            request?.let {
                repository?.insertNotes(it)
            }
            callback?.onSave()
            dismiss()
        }
    }

    interface NoteCallback {
        fun onSave()
    }

}