package com.mogalabs.notes.feature.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mogalabs.notes.R
import com.mogalabs.notes.data.repository.NoteModel
import com.mogalabs.notes.databinding.NoteItemBinding

class NoteAdapter : ListAdapter<NoteModel, NoteAdapter.NoteHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
                        && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnNoteClickListener? = null

    fun setOnItemClickListener(listener: OnNoteClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(getItem(position),listener)
    }

    fun getNoteAt(position: Int): NoteModel {
        return getItem(position)
    }

    inner class NoteHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel, clickListener: OnNoteClickListener?) {
            binding.note = note
            binding.clicklistenr = clickListener
            binding.executePendingBindings()
        }
    }
}
