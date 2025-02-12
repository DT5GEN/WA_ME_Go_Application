package com.dt5gen.wamegoapplication.presentation

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.dt5gen.wamegoapplication.domain.ClipboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClipboardViewModel @Inject constructor(
    application: Application,
    private val clipboardUseCase: ClipboardUseCase
) : AndroidViewModel(application), LifecycleObserver {

    private val clipboardManager = application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    var phoneNumber = mutableStateOf("")
        private set

    init {
        loadPhoneNumber()  // Загружаем номер при старте
        clipboardManager.addPrimaryClipChangedListener {
            checkClipboardForPhoneNumber()
        }
    }

    fun loadPhoneNumber() {
        clipboardUseCase.getFormattedPhoneNumber()?.let {
            phoneNumber.value = it
        }
    }

    fun updatePhoneNumber(newValue: String) {
        phoneNumber.value = clipboardUseCase.formatPhoneNumber(newValue)
    }

    fun getWhatsAppUrl(): String {
        return "https://wa.me/${phoneNumber.value}"
    }

    fun checkClipboardForPhoneNumber() {
        viewModelScope.launch {
            val newNumber = clipboardUseCase.getClipboardPhoneNumber(getApplication())

            if (!newNumber.isNullOrEmpty() && newNumber != phoneNumber.value) {
                phoneNumber.value = newNumber  // Только если номер изменился
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        checkClipboardForPhoneNumber()  // Проверяем буфер при возврате в приложение
    }
}
