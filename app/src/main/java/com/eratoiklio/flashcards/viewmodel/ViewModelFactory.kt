package com.eratoiklio.flashcards.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eratoiklio.flashcards.ui.main.MainViewModel

class ViewModelFactory(private val application: Application?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(application!!) as T

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
}