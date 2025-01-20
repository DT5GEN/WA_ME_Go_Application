package com.dt5gen.wamegoapplication

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.dt5gen.wamegoapplication.presentation.ClipboardViewModel
import com.dt5gen.wamegoapplication.presentation.screens.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val viewModel: ClipboardViewModel = hiltViewModel()
                MainScreen(viewModel)
            }
        }
    }
}
