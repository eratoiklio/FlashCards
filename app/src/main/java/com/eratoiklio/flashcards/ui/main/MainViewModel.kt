package com.eratoiklio.flashcards.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.data.FlashCardRepository
import com.eratoiklio.flashcards.model.FlashCard
import com.eratoiklio.flashcards.ui.flashcard.WordListAdapter
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    private val repository: FlashCardRepository
    val allFlashCards: LiveData<List<FlashCard>>
    val adapter = WordListAdapter(application)

    init {
        val wordsDao = FlashCardDb.getDatabase(application).flashCardDao()
        repository = FlashCardRepository(wordsDao)
        allFlashCards = repository.allFlashCards
    }

    fun insert(word: FlashCard) = viewModelScope.launch {
        repository.insert(word)
    }

    fun insertTmp() = viewModelScope.launch {
        repository.insert(FlashCard(mainLanguageWord = "Test", secondLanguageWord = "Test"))
    }
}
