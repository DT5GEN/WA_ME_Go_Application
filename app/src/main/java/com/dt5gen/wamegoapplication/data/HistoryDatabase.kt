package com.dt5gen.wamegoapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HistoryEntity::class], version = 4, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        // ✅ Миграция с версии 3 на 4 (Обновляем структуру, если есть новые поля)
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Например, если добавляем новое поле createdAt
                database.execSQL("ALTER TABLE history ADD COLUMN createdAt INTEGER DEFAULT 0 NOT NULL")
            }
        }

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                )
                    .addMigrations(MIGRATION_3_4) // ✅ Добавляем миграцию
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
