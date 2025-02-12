package com.dt5gen.wamegoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dt5gen.wamegoapplication.presentation.ClipboardViewModel
import com.dt5gen.wamegoapplication.presentation.screens.MainScreen
import com.dt5gen.wamegoapplication.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.LifecycleOwner

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем Edge-to-Edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // Добавляем фон из темы
                ) {
                    val viewModel: ClipboardViewModel = hiltViewModel()
                    val lifecycleOwner: LifecycleOwner = this

                    lifecycleOwner.lifecycle.addObserver(viewModel) // Подключаем ViewModel к жизненному циклу

                    MainScreen(viewModel)
                }
            }
        }
    }
}
