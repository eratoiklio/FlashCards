package com.eratoiklio.flashcards.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flash_card")
data class FlashCard(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                     val mainLanguageWord: String, val secondLanguageWord: String,
                     val description: String = "", val uses: String = "", val firstShowMainLanguage: Boolean = true)

data class FlashCardSet(val flashCards: Set<FlashCard>, val name: String, val description: String)