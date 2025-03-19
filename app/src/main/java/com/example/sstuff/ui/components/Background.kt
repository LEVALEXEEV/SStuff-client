package com.example.sstuff.ui.components

import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedGradientBackground() {
    val infiniteTransition = rememberInfiniteTransition()

    // Первый градиент (основной, медленный)
    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 800f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Второй градиент (дополнительный, движется по-другому)
    val offset3 by infiniteTransition.animateFloat(
        initialValue = -500f,
        targetValue = 1300f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offset4 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16000, easing = EaseInOutBack),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Первый (основной) градиент
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color.Black, Color.DarkGray, Color.Gray, Color.White, Color.Gray, Color.Black),
                        start = Offset(offset1, offset2),
                        end = Offset(1200f - offset2, 800f - offset1)
                    )
                )
        )

        // Второй (дополнительный) градиент — добавляет больше глубины
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color.Black, Color.Gray, Color.White, Color.Gray, Color.Black),
                        start = Offset(offset3, offset4),
                        end = Offset(1300f - offset4, 900f - offset3)
                    ),
                    alpha = 0.5f // Делаем его полупрозрачным, чтобы он смешивался с основным
                )
        )
    }
}
