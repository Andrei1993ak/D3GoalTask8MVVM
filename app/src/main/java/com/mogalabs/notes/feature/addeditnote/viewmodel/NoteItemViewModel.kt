package com.mogalabs.notes.feature.addeditnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mogalabs.notes.DEFAULT_ID
import com.mogalabs.notes.data.repository.NoteModel
import com.mogalabs.notes.data.repository.NoteRepository

class NoteItemViewModel(private val repository: NoteRepository) : ViewModel() {
    private lateinit var note: LiveData<NoteModel>
    private var noteItemId = DEFAULT_ID

    fun onCreate(id: Int) {
        noteItemId = id
        note = repository.getNote(noteItemId)
    }

    fun insertOrUpdate(note: NoteModel) {
        if (noteItemId == DEFAULT_ID) {
            repository.insert(note)
        } else {
            repository.update(note)
        }
    }

    fun getNote() = note

    fun getTitle(): String {
        return if (noteItemId == DEFAULT_ID) {
            "Add Note"
        } else {
            "Edit Note"
        }
    }
}