package com.eratoiklio.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eratoiklio.flashcards.model.*

@Dao
interface FlashCardDao {

    @Transaction
    @Query("SELECT * from flash_card ORDER BY mainLanguageWord ASC")
    fun getFlashCardsWithSets(): LiveData<List<FlashCardWithSets>>

    @Transaction
    @Query("SELECT * from flash_card_set ORDER BY name ASC")
    fun getSetsWithFlashCards(): LiveData<List<SetWithFlashCards>>

    @Query("SELECT * from flash_card_set ORDER BY name ASC")
    fun getAllSets(): LiveData<List<FlashCardSet>>

    @Query("SELECT * FROM flash_card ORDER BY mainLanguageWord ASC")
    fun getAllFlashCards(): LiveData<List<FlashCard>>

    @Query("DELETE FROM flash_card")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: FlashCard): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSet(set: FlashCardSet): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJoin(join: FlashCardSetJoin)
}