package com.dt5gen.wamegoapplication.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dt5gen.wamegoapplication.domain.ClipboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
//class ClipboardViewModel @Inject constructor(
//    private val clipboardUseCase: ClipboardUseCase
//) : ViewModel() {
//    var phoneNumber by mutableStateOf("")
//        private set
//
//    init {
//        loadPhoneNumber()
//    }
//
//    fun loadPhoneNumber() {
//        clipboardUseCase.getFormattedPhoneNumber()?.let {
//            phoneNumber = it
//        }
//    }
//
//    fun updatePhoneNumber(newValue: String) {
//        phoneNumber = newValue
//    }
//
//    fun getWhatsAppUrl(): String {
//        return "https://wa.me/$phoneNumber"
//    }
//}


@HiltViewModel
class ClipboardViewModel @Inject constructor(
    private val clipboardUseCase: ClipboardUseCase
) : ViewModel() {

    var phoneNumber by mutableStateOf("")
        private set

    var isEditing by mutableStateOf(false) // Флаг для управления форматированием при вводе
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
        if (newValue.isEmpty()) {
            phoneNumber = ""
        } else {
            phoneNumber = clipboardUseCase.formatPhoneNumber(newValue)
        }
    }

    fun onFocusChanged(isFocused: Boolean) {
        isEditing = isFocused
        if (!isFocused) {
            phoneNumber =
                clipboardUseCase.formatPhoneNumber(phoneNumber) // Форматируем только при потере фокуса
        }
    }

    fun getWhatsAppUrl(): String {
        val formattedNumber = "+${phoneNumber.replace("+", "")}" // Добавляем "+" только перед отправкой
        return "https://wa.me/$formattedNumber"
    }


    fun checkClipboardForPhoneNumber(context: Context) {
        clipboardUseCase.getClipboardPhoneNumber(context)?.let {
            phoneNumber = it
        }
    }
}
