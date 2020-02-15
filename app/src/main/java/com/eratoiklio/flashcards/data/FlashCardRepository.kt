package com.eratoiklio.flashcards.data

import androidx.lifecycle.LiveData
import com.eratoiklio.flashcards.model.FlashCard

class FlashCardRepository(private val flashCardDao: FlashCardDao) {

    val allFlashCards: LiveData<List<FlashCard>> = flashCardDao.getFlashCards()

    suspend fun insert(card: FlashCard) {
        flashCardDao.insert(card)
    }
}