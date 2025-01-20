package com.dt5gen.wamegoapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dt5gen.wamegoapplication.domain.ClipboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClipboardViewModel @Inject constructor(
    private val clipboardUseCase: ClipboardUseCase
) : ViewModel() {
    var phoneNumber by mutableStateOf("")
        private set

    init {
        loadPhoneNumber()
    }

    fun loadPhoneNumber() {
        clipboardUseCase.getFormattedPhoneNumber()?.let {
            phoneNumber = it
        }
    }

    fun updatePhoneNumber(newValue: String) {
        phoneNumber = newValue
    }

    fun getWhatsAppUrl(): String {
        return "https://wa.me/$phoneNumber"
    }
}
