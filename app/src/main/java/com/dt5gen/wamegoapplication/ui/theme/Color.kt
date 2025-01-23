package com.dt5gen.wamegoapplication.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007AFF),
    onPrimary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black
)

val DarkColorScheme = darkColorScheme(
    background = Color(0xFF121212),  // Тёмный фон как в системной теме
    surface = Color(0xFF1E1E1E), // Цвет карточек и элементов
    onBackground = Color.White,
    onSurface = Color.White
)
