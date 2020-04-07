package com.eratoiklio.flashcards

import android.app.Application
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class FlashCardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ViewModelFactory.init(this)
    }
}