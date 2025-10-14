package com.akshar.messaging.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akshar.messaging.data.local.dao.ChatDao
import com.akshar.messaging.data.local.dao.MessageDao
import com.akshar.messaging.data.local.entities.ChatEntity
import com.akshar.messaging.data.local.entities.MessageEntity

@Database(
    entities = [ChatEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AksharDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AksharDatabase? = null

        fun getDatabase(context: Context): AksharDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AksharDatabase::class.java,
                    "akshar_messaging_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

