package com.eratoiklio.flashcards.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.eratoiklio.flashcards.R
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.SetWithFlashCards
import com.eratoiklio.flashcards.ui.flashcard.SetListAdapter

class MainViewModel(application: Application, private val navController: NavController) :
    ViewModel() {

    private val dao: FlashCardDao = FlashCardDb.getDatabase(application).flashCardDao()
    val allSets: LiveData<List<FlashCardSet>>
    val adapter = SetListAdapter(application)
    val subMenuVisibility = MutableLiveData<Int>(View.GONE)

    init {
        allSets = dao.getAllSets()
    }

    fun showSubMenu() {
        if (subMenuVisibility.value == View.GONE) {
            subMenuVisibility.value = View.VISIBLE
        } else {
            subMenuVisibility.value = View.GONE
        }
    }

    fun createFlashCard() {
        navController.navigate(R.id.newFlashCardFragment)
    }

    fun createFlashCardSet() {
        navController.navigate(R.id.newFlashCardSetFragment)
    }
}
