package com.eratoiklio.flashcards.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.SetWithFlashCards
import com.eratoiklio.flashcards.ui.flashcard.SetListAdapter
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    private val dao: FlashCardDao = FlashCardDb.getDatabase(application).flashCardDao()
    val allFlashCards: LiveData<List<SetWithFlashCards>>
    val adapter = SetListAdapter(application)

    init {
        allFlashCards = dao.getSets()
    }

    fun insertTmp() = viewModelScope.launch {
        dao.insertSet(FlashCardSet(name = "Test", description = "Test"))
    }
}
