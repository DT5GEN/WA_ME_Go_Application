package com.dt5gen.wamegoapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY lastUsed DESC")
    fun getAllHistory(): Flow<List<HistoryEntity>>  // Получаем историю

    @Query("SELECT * FROM history WHERE phoneNumber = :phoneNumber LIMIT 1")
    suspend fun getEntryByNumber(phoneNumber: String): HistoryEntity?  // Проверяем, есть ли номер

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryEntity)  // Вставка

    @Query("UPDATE history SET lastUsed = :timestamp WHERE phoneNumber = :phoneNumber")
    suspend fun updateTimestamp(phoneNumber: String, timestamp: Long)  // Обновление времени
}

