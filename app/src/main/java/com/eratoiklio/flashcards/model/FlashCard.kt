package com.eratoiklio.flashcards.model

import androidx.room.*

@Entity(tableName = "flash_card")
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val cardId: Long = 0,
    val mainLanguageWord: String, val secondLanguageWord: String,
    val description: String = "", val uses: String = ""
)

@Entity(tableName = "flash_card_set")
data class FlashCardSet(
    @PrimaryKey(autoGenerate = true) val setId: Long = 0,
    val name: String, val description: String
)

@Entity(primaryKeys = ["cardId", "setId"])
data class FlashCardSetJoin(val cardId: Long, val setId: Long)

data class SetWithFlashCards(
    @Embedded val set: FlashCardSet,
    @Relation(
        parentColumn = "setId",
        entity = FlashCard::class,
        entityColumn = "cardId",
        associateBy = Junction(
            value = FlashCardSetJoin::class,
            parentColumn = "setId",
            entityColumn = "cardId"
        )
    )
    val cards: List<FlashCard>
)

data class FlashCardWithSets(
    @Embedded val card: FlashCard,
    @Relation(
        parentColumn = "cardId",
        entity = FlashCardSet::class,
        entityColumn = "setId",
        associateBy = Junction(
            value = FlashCardSetJoin::class,
            parentColumn = "cardId",
            entityColumn = "setId"
        )
    )
    val sets: List<FlashCardSet>
)