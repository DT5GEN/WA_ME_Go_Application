package com.dt5gen.wamegoapplication.data


import android.content.ClipboardManager
import android.content.Context
import javax.inject.Inject

class ClipboardRepository @Inject constructor(private val context: Context) {
    fun getClipboardText(): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = clipboard.primaryClip
        return clipData?.getItemAt(0)?.text?.toString()
    }
}
