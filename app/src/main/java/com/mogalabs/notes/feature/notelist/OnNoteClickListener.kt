package com.mogalabs.notes.feature.notelist

import com.mogalabs.notes.data.repository.NoteModel

interface OnNoteClickListener {
    fun onNoteClick(note: NoteModel)
}
