package com.mogalabs.notes.di

import com.mogalabs.notes.data.repository.DataBaseNoteRepositoryImpl
import com.mogalabs.notes.data.repository.NoteRepository
import com.mogalabs.notes.feature.addeditnote.viewmodel.NoteItemViewModel
import com.mogalabs.notes.feature.notelist.viewmodel.NoteListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val userRepositoryModule = module {

    single { DataBaseNoteRepositoryImpl(androidContext()) as NoteRepository }

    viewModel { NoteItemViewModel(get()) }
    viewModel { NoteListViewModel(get()) }
}
