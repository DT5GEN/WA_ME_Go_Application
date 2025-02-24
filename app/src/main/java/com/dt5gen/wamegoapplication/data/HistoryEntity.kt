package com.dt5gen.wamegoapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey val phoneNumber: String,  // Уникальный номер
    val lastUsed: Long  // Последний вызов (время в миллисекундах)
)
