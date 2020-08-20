package com.luqman.roomexercize.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luqman.roomexercize.R
import com.luqman.roomexercize.repository.model.Note

class NoteAdapter(
    private val callback : OnItemClicked
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item, parent, false
        )

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val model = currentList[position]
        holder.textNote.text = model.note

        holder.buttonDelete.setOnClickListener {
            callback.onDelete(model)
        }
    }

    interface OnItemClicked {
        fun onDelete(note: Note)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNote: TextView = itemView.findViewById(R.id.text_note)
        val buttonDelete: Button = itemView.findViewById(R.id.button_delete)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.note == newItem.note
            }
        }
    }
}
