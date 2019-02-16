package com.mogalabs.notes.data.db

import com.mogalabs.notes.DEFAULT_ID
import com.mogalabs.notes.data.repository.NoteModel

class DataBaseNoteModelMapper {
    companion object {
        fun map(noteModel: NoteModel): Note {
            val note = Note(noteModel.title, noteModel.description, noteModel.priority)

            if (noteModel.id != DEFAULT_ID) {
                note.id = noteModel.id
            }

            return note
        }

        fun map(note: Note) = NoteModel(note.title, note.description, note.priority, note.id)
    }
}
