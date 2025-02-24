package com.dt5gen.wamegoapplication.domain

import com.dt5gen.wamegoapplication.data.HistoryDao
import com.dt5gen.wamegoapplication.data.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val historyDao: HistoryDao) {

    val history: Flow<List<HistoryEntity>> = historyDao.getAllHistory()

    suspend fun addNumber(phoneNumber: String) {
        val existingEntry = historyDao.getEntryByNumber(phoneNumber)
        if (existingEntry == null) {
            historyDao.insert(HistoryEntity(phoneNumber = phoneNumber, lastUsed = System.currentTimeMillis()))
        } else {
            historyDao.updateTimestamp(phoneNumber, System.currentTimeMillis())
        }
    }
}
