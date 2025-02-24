package com.dt5gen.wamegoapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.dt5gen.wamegoapplication.presentation.screens.MainScreen
import com.dt5gen.wamegoapplication.presentation.ClipboardViewModel
import com.dt5gen.wamegoapplication.presentation.viewmodel.HistoryViewModel
import com.dt5gen.wamegoapplication.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var clipboardViewModel: ClipboardViewModel // ✅ Теперь создаём один раз
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        clipboardViewModel = ViewModelProvider(this)[ClipboardViewModel::class.java] // ✅ Создаём один раз
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(clipboardViewModel, historyViewModel) // ✅ Передаём ViewModel в экран
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        clipboardViewModel.updatePhoneNumber("")  // ✅ Сброс перед обновлением
        Handler(Looper.getMainLooper()).postDelayed({
            clipboardViewModel.checkClipboardForPhoneNumber()
        }, 100)  // ✅ Принудительная проверка через 100 мс
    }
}
