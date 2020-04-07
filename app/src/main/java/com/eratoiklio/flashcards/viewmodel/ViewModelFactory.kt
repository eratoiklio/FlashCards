package com.eratoiklio.flashcards.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb

object ViewModelFactory : ViewModelProvider.Factory {

    private lateinit var dao: FlashCardDao

    fun init(application: Application) {
        dao = FlashCardDb.getDatabase(application).flashCardDao()
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(dao) as T

            modelClass.isAssignableFrom(NewFlashCardSetViewModel::class.java) ->
                NewFlashCardSetViewModel(dao) as T

            modelClass.isAssignableFrom(NewFlashCardViewModel::class.java) ->
                NewFlashCardViewModel(dao) as T

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
}