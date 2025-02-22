package com.dt5gen.wamegoapplication.presentation

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dt5gen.wamegoapplication.domain.ClipboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClipboardViewModel @Inject constructor(
    application: Application,
    private val clipboardUseCase: ClipboardUseCase
) : AndroidViewModel(application) {

    private val clipboardManager = application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    init {
        loadPhoneNumber()  // Загружаем номер при старте
        clipboardManager.addPrimaryClipChangedListener {
            checkClipboardForPhoneNumber()
        }
    }

    fun loadPhoneNumber() {
        clipboardUseCase.getFormattedPhoneNumber()?.let {
            _phoneNumber.value = it
        }
    }

    fun updatePhoneNumber(newValue: String) {
        _phoneNumber.value = clipboardUseCase.formatPhoneNumber(newValue)
    }

    fun getWhatsAppUrl(): String {
        return "https://wa.me/${_phoneNumber.value}"
    }

    fun checkClipboardForPhoneNumber() {
        viewModelScope.launch {
            val newNumber = clipboardUseCase.getClipboardPhoneNumber(getApplication())

            if (!newNumber.isNullOrEmpty()) {
                // ✅ Сброс перед обновлением (гарантирует срабатывание StateFlow)
                _phoneNumber.value = ""
                delay(50)  // Небольшая задержка перед обновлением
                _phoneNumber.value = newNumber
            }
        }
    }
}
