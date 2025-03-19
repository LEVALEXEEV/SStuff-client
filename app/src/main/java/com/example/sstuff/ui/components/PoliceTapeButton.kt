package com.example.sstuff.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sstuff.akony
import com.example.sstuff.dashedBorder

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun PoliceTapeButton(text: String, navController: NavController, route: String, rotation: Float) {
    val shift = if (text.startsWith("cart")) 90 else 0
    val direction = if (rotation < 0) -1 else 1
    val initialValue = if (rotation < 0) 70f else 50f + shift
    val targetValue = if (rotation < 0) -290f else -320f + shift
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .width(600.dp)
            .height(40.dp)
            .graphicsLayer(rotationZ = rotation, scaleX = 1.5f, scaleY = 1.5f)
            .clickable { navController.navigate(route) }
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            .border(1.dp, Color.Black)
            .dashedBorder(
                Color.Black, shape = RectangleShape,
                strokeWidth = 4.dp, gapLength = 16.dp, dashLength = 16.dp, cap = StrokeCap.Square
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .offset(x = (direction * offsetX).dp)
        ) {
            // Дублируем текст для бесшовной анимации
            for (i in 0..2) {
                Text(
                    text = "⚠ $text", // Повторяем текст для плавного эффекта
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = akony,
                    modifier = Modifier
                        .graphicsLayer(translationX = offsetX + i * 300f) // Смещение второго блока текста
                )
            }
        }
    }
}