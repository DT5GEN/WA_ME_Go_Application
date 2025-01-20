package com.dt5gen.wamegoapplication.domain

import com.dt5gen.wamegoapplication.data.ClipboardRepository
import javax.inject.Inject

class ClipboardUseCase @Inject constructor(private val repository: ClipboardRepository) {
    fun getFormattedPhoneNumber(): String? {
        return repository.getClipboardText()?.replace(Regex("[^0-9+]"), "")
    }
}
