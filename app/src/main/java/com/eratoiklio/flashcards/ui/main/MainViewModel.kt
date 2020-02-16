package com.eratoiklio.flashcards.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.eratoiklio.flashcards.R
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.SetWithFlashCards
import com.eratoiklio.flashcards.ui.flashcard.SetListAdapter

class MainViewModel(application: Application, private val navController: NavController) : ViewModel() {

    private val dao: FlashCardDao = FlashCardDb.getDatabase(application).flashCardDao()
    val allSets: LiveData<List<FlashCardSet>>
    val adapter = SetListAdapter(application)

    init {
        allSets = dao.getAllSets()
    }

    fun insertTmp() {
        navController.navigate(R.id.newFlashCardSetFragment)
    }
}
