package com.example.sstuff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sstuff.akony
import com.example.sstuff.data.UserPreferences
import com.example.sstuff.data.addToCart
import com.example.sstuff.data.getCart
import com.example.sstuff.data.getCatalog
import com.example.sstuff.data.models.Product
import com.example.sstuff.data.removeFromCart
import com.example.sstuff.ui.components.Swiper
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(productId: String?, navController: NavController) {
    val scope = rememberCoroutineScope()
    var cartItems by remember { mutableStateOf<List<String>>(emptyList()) }
    var token by remember { mutableStateOf<String?>(null) }
    val added = remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        UserPreferences.getUserData(context).collect { user ->
            token = user.first
        }
    }

    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(productId) {
        val catalog = getCatalog()
        product = catalog.find { it.id == productId }
    }
    product?.let {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .background(
                        shape = RoundedCornerShape(50.dp),
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White,
                                Color.Black.copy(0.15f),
                            ),
                            radius = 600f,
                        )
                    )
            ) {
                Swiper(product!!.imageUrls)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(product!!.name, fontSize = 24.sp, fontFamily = akony, color = Color.White, textAlign = TextAlign.Center)
            Text(
                "${product!!.price} rub",
                fontSize = 20.sp,
                fontFamily = akony,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp),
            )
            Text(
                product!!.description,
                modifier = Modifier.padding(vertical = 16.dp),
                fontFamily = akony,
                color = Color.White
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(Color.White, Color.Black, Color.DarkGray, Color.White),
                onClick = {
                    if (token == null) navController.navigate("login")
                    else scope.launch {
                        if (!added.value) {
                            addToCart(token, product!!.id)
                            cartItems = getCart(token)
                        } else {
                            removeFromCart(token, product!!.id)
                        }
                        added.value = !added.value
                    }
                }
            ) {
                Text(
                    if (added.value) "DEL FROM CART" else "ADD TO CART",
                    fontFamily = akony,
                    fontSize = 10.sp
                )
            }
        }
    }
}
