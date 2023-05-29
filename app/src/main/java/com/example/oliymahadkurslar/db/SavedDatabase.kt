package com.example.oliymahadkurslar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.oliymahadkurslar.model.SavedModel


@Database(
    entities = [SavedModel::class],
    version = 1
)
abstract class SavedDatabase : RoomDatabase() {

    abstract fun getSaved(): SavedDao

    companion object {
        @Volatile
        private var instance: SavedDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SavedDatabase::class.java,
                "saved_db"
            ).build()
    }

}