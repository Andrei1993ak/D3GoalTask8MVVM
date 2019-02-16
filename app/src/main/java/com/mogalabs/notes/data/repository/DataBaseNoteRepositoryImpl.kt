package com.mogalabs.notes.data.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mogalabs.notes.DEFAULT_ID
import com.mogalabs.notes.data.db.DataBaseNoteModelMapper
import com.mogalabs.notes.data.db.Note
import com.mogalabs.notes.data.db.NoteDao
import com.mogalabs.notes.data.db.NoteDatabase


class DataBaseNoteRepositoryImpl(context: Context) : NoteRepository {

    private var noteDao: NoteDao

    private var allNotes: LiveData<List<NoteModel>>

    init {
        val database: NoteDatabase = NoteDatabase.getInstance(context)
        noteDao = database.noteDao()
        allNotes = Transformations.map(noteDao.getAllNotes()) {
            it.map { note ->
                DataBaseNoteModelMapper.map(note)
            }
        }
    }

    override fun insert(note: NoteModel) {
        InsertNoteAsyncTask(noteDao).execute(DataBaseNoteModelMapper.map(note))
    }

    override fun update(note: NoteModel) {
        UpdateNoteAsyncTask(noteDao).execute(DataBaseNoteModelMapper.map(note))
    }


    override fun delete(note: NoteModel) {
        DeleteNoteAsyncTask(noteDao).execute(DataBaseNoteModelMapper.map(note))
    }

    override fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    override fun getNote(id: Int): LiveData<NoteModel> {
        return if (id == DEFAULT_ID) {
            getEmptyNoteLiveData()
        } else {
            Transformations.map(noteDao.getNote(id)) { DataBaseNoteModelMapper.map(it) }
        }
    }

    override fun getAllNotes(): LiveData<List<NoteModel>> {
        return allNotes
    }

    private fun getEmptyNoteLiveData(): LiveData<NoteModel> {
        return object : LiveData<NoteModel>() {
            override fun getValue(): NoteModel {
                return NoteModel("", "", 0, DEFAULT_ID)
            }
        }
    }

    companion object {
        private class InsertNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
            override fun doInBackground(vararg p0: Note) {
                noteDao.insert(p0[0])
            }
        }

        private class UpdateNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
            override fun doInBackground(vararg p0: Note) {
                noteDao.update(p0[0])
            }
        }

        private class DeleteNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {
            override fun doInBackground(vararg p0: Note) {
                noteDao.delete(p0[0])
            }
        }

        private class DeleteAllNotesAsyncTask(val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg p0: Unit) {
                noteDao.deleteAllNotes()
            }
        }
    }
}