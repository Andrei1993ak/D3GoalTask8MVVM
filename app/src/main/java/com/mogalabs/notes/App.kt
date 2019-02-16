package com.mogalabs.notes

import android.app.Application
import com.mogalabs.notes.di.userRepositoryModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(userRepositoryModule))
    }
}