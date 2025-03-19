package com.example.sstuff.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sstuff.akony

@Composable
fun EmptyCart() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 13.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            Modifier
                .background(
                    shape = RoundedCornerShape(10.dp),
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(0.15f),
                            Color.Black.copy(0.3f),
                        ),
                        radius = 300f,
                    )
                )
        ) {
            Text(
                "EMPTY",
                modifier = Modifier.fillMaxWidth().padding(40.dp),
                textAlign = TextAlign.Center,
                fontFamily = akony,
                fontSize = 26.sp
            )
        }
    }
}