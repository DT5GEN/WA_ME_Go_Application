package com.dt5gen.wamegoapplication.domain

import android.content.ClipboardManager
import android.content.Context
import com.dt5gen.wamegoapplication.data.ClipboardRepository
import javax.inject.Inject

class ClipboardUseCase @Inject constructor(private val repository: ClipboardRepository) {

    fun getFormattedPhoneNumber(): String? {
        val rawNumber = repository.getClipboardText() ?: return null
        return formatPhoneNumber(rawNumber)
    }

    fun getClipboardPhoneNumber(context: Context): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip

        if (clipData != null && clipData.itemCount > 0) {
            val text = clipData.getItemAt(0).text?.toString() ?: return null
            return formatPhoneNumber(text) // Форматируем номер
        }
        return null
    }

    fun formatPhoneNumber(phone: String): String {
        var cleanNumber = phone.replace(Regex("[^0-9+]"), "") // Удаляем все ненужные символы

        if (cleanNumber.isEmpty()) return "" // Разрешаем полностью очищать поле

        // Если номер уже в международном формате — просто возвращаем его (до 15 символов)
        if (cleanNumber.startsWith("+")) {
            return cleanNumber.take(15)
        }

        // Если начинается с "8" (Россия) и длина 11 цифр — заменяем на "+7"
        if (cleanNumber.startsWith("8") && cleanNumber.length == 11) {
            cleanNumber = "+7" + cleanNumber.substring(1)
        }

        // Если номер не содержит "+", добавляем его только если это российский номер
        if (!cleanNumber.startsWith("+")) {
            cleanNumber = "+$cleanNumber"
        }

        return cleanNumber.take(15) // Ограничиваем максимальную длину
    }
}
