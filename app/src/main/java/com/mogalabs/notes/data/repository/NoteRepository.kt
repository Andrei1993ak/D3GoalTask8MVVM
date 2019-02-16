package com.mogalabs.notes.data.repository

import androidx.lifecycle.LiveData

interface NoteRepository {
    fun getNote(id: Int): LiveData<NoteModel>
    fun getAllNotes(): LiveData<List<NoteModel>>
    fun insert(note: NoteModel)
    fun update(note: NoteModel)
    fun delete(note: NoteModel)
    fun deleteAllNotes()
}