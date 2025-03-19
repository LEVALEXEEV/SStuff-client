package com.example.sstuff.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sstuff.akony
import com.example.sstuff.data.models.Product

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }.background(
            shape = RoundedCornerShape(50.dp),
            brush = Brush.radialGradient(colors = listOf(
                Color.White,
                Color.Black.copy(0.15f),
            ),
                radius = 600f,
            )
        )
    )
    {
        Column(modifier = Modifier.padding(16.dp).align(Alignment.Center)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrls.firstOrNull())
                    .crossfade(true)
                    .build(),
                contentDescription = product.name,
                modifier = Modifier.size(300.dp).align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(product.name, fontSize = 20.sp, fontFamily = akony, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text("${product.price} rub", fontFamily = akony)
            }
        }
    }
}
