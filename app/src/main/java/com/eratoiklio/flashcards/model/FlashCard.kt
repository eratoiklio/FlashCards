package com.eratoiklio.flashcards.model

data class FlashCard(val mainLanguageWord: String, val secondLanguageWord: String,
                     val description: String, val uses: String, val firstShowMainLanguage: Boolean)

data class FlashCardSet(val flashCards: Set<FlashCard>, val name: String, val description: String)