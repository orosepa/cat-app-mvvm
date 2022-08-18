package com.example.catapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatImageEntity::class], version = 1)
abstract class LikedCatsDatabase: RoomDatabase() {
    abstract val dao: LikedCatsDao

    companion object {
        private var instance: LikedCatsDatabase? = null

        fun getDatabase(context: Context): LikedCatsDatabase? {
            if (instance == null) {
                synchronized(LikedCatsDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LikedCatsDatabase::class.java,
                        "liked_cats.db"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}