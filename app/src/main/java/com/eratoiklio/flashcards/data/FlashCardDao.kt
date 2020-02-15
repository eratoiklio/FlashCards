package com.eratoiklio.flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eratoiklio.flashcards.model.FlashCard

@Dao
interface FlashCardDao {

    @Query("SELECT * from flash_card ORDER BY mainLanguageWord ASC")
    fun getFlashCards(): LiveData<List<FlashCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: FlashCard)

    @Query("DELETE FROM flash_card")
    suspend fun deleteAll()
}