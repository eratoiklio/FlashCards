package com.eratoiklio.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eratoiklio.flashcards.model.*

@Dao
interface FlashCardDao {

    @Transaction
    @Query("SELECT * from flash_card ORDER BY mainLanguageWord ASC")
    fun getFlashCards(): LiveData<List<FlashCardWithSets>>

    @Transaction
    @Query("SELECT * from flash_card_set ORDER BY name ASC")
    fun getSets(): LiveData<List<SetWithFlashCards>>

    @Query("DELETE FROM flash_card")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: FlashCard)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSet(set: FlashCardSet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJoin(join: FlashCardSetJoin)
}