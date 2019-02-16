package com.mogalabs.notes.data.repository

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class NoteModel(
    var _title: String,
    var _description: String,
    var _priority: Int,
    var _id: Int = 0
) : BaseObservable() {

    val id: Int
        get() = _id

    var title: String
        @Bindable get() = _title
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }

    var priority: Int
        @Bindable get() = _priority
        set(value) {
            _priority = value
            notifyPropertyChanged(BR.priority)
        }

    var description: String
        @Bindable get() = _description
        set(value) {
            _description = value
            notifyPropertyChanged(BR.description)
        }
}
