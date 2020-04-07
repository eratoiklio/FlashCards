package com.eratoiklio.flashcards.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.eratoiklio.flashcards.R
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.ui.flashcard.SetListAdapter

class MainViewModel(dao: FlashCardDao) : ViewModel() {

    val allSets: LiveData<List<FlashCardSet>> = dao.getAllSets()
    val adapter = SetListAdapter()
    val subMenuVisibility = MutableLiveData<Int>(View.GONE)

    fun showSubMenu() {
        if (subMenuVisibility.value == View.GONE) {
            subMenuVisibility.value = View.VISIBLE
        } else {
            subMenuVisibility.value = View.GONE
        }
    }

    fun createFlashCard(view: View) {
        view.findNavController().navigate(R.id.newFlashCardFragment)
    }

    fun createFlashCardSet(view: View) {
        view.findNavController().navigate(R.id.newFlashCardSetFragment)
    }
}
