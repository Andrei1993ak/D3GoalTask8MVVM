package com.mogalabs.notes.feature.notelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mogalabs.notes.data.repository.NoteModel
import com.mogalabs.notes.data.repository.NoteRepository

class NoteListViewModel(private val repository: NoteRepository) : ViewModel() {
    private var allNotes: LiveData<List<NoteModel>> = repository.getAllNotes()

    fun delete(note: NoteModel) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<NoteModel>> {
        return allNotes
    }
}