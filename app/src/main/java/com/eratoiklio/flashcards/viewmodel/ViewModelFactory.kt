package com.eratoiklio.flashcards.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.eratoiklio.flashcards.ui.flashcard.NewFlashCardSetViewModel
import com.eratoiklio.flashcards.ui.flashcard.NewFlashCardViewModel
import com.eratoiklio.flashcards.ui.main.MainViewModel

class ViewModelFactory private constructor(
    private val application: Application,
    private val navController: NavController
) : ViewModelProvider.Factory {

    companion object {
        lateinit var instance: ViewModelFactory

        fun init(application: Application, navController: NavController) {
            instance = ViewModelFactory(application, navController)
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(application, navController) as T

            modelClass.isAssignableFrom(NewFlashCardSetViewModel::class.java) ->
                NewFlashCardSetViewModel(application, navController) as T

            modelClass.isAssignableFrom(NewFlashCardViewModel::class.java) ->
                NewFlashCardViewModel(application, navController) as T

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
}