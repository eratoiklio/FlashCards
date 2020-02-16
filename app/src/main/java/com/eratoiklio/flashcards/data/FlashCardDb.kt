package com.eratoiklio.flashcards.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eratoiklio.flashcards.model.FlashCard
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.FlashCardSetJoin

@Database(entities = [FlashCard::class, FlashCardSet::class, FlashCardSetJoin::class], version = 1, exportSchema = false)
abstract class FlashCardDb : RoomDatabase() {

    abstract fun flashCardDao(): FlashCardDao

    companion object {
        @Volatile
        private var INSTANCE: FlashCardDb? = null

        fun getDatabase(context: Context): FlashCardDb {
            INSTANCE?.apply {
                return this
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashCardDb::class.java,
                    "flash_card_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}