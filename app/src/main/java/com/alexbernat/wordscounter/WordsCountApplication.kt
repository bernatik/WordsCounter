package com.alexbernat.wordscounter

import android.app.Application
import com.alexbernat.wordscounter.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WordsCountApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WordsCountApplication)
            modules(appModule)
        }
    }
}