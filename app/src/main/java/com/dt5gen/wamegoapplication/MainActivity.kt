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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.dt5gen.wamegoapplication.presentation.ClipboardViewModel
import com.dt5gen.wamegoapplication.presentation.screens.MainScreen
import com.dt5gen.wamegoapplication.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: ClipboardViewModel = hiltViewModel()
                    MainScreen(viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val viewModel: ClipboardViewModel = ViewModelProvider(this)[ClipboardViewModel::class.java]

        viewModel.updatePhoneNumber("")  // ✅ Сброс перед обновлением
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.checkClipboardForPhoneNumber()
        }, 100)  // ✅ Принудительная проверка через 100 мс
    }
}

