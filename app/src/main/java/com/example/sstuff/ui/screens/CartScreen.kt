package com.example.sstuff.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sstuff.akony
import androidx.compose.ui.text.style.TextAlign
import com.example.sstuff.data.UserPreferences
import com.example.sstuff.data.getCart
import com.example.sstuff.data.getCatalog
import com.example.sstuff.data.models.Product
import com.example.sstuff.data.removeFromCart
import com.example.sstuff.ui.components.EmptyCart
import kotlinx.coroutines.launch

@Composable
fun CartScreen() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var cartItems by remember { mutableStateOf<List<String>>(emptyList()) }
    var token by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        UserPreferences.getUserData(context).collect { user ->
            token = user.first
            if (token != null) {
                cartItems = getCart(token)
            }
        }
    }

    var catalog by remember { mutableStateOf<List<Product>>(emptyList()) }
    LaunchedEffect(Unit) {
        catalog = getCatalog()
    }

    Text("CART", fontSize = 30.sp, fontFamily = akony, color = Color.White, modifier = Modifier.fillMaxWidth().padding(top = 10.dp), textAlign = TextAlign.Center)
    if (cartItems.isEmpty()) EmptyCart()
    else {
        Column(modifier = Modifier.padding(16.dp).padding(top = 20.dp)) {
            LazyColumn {
                items(cartItems) { id ->
                    val product = catalog.find { product -> product.id == id }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(60.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${product!!.name} - ${product.price} rub.",
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f),
                            fontFamily = akony,
                            color = Color.White
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    removeFromCart(token, product.id)
                                    cartItems = getCart(token)
                                }
                            },
                            colors = ButtonColors(
                                Color.White,
                                Color.Black,
                                Color.DarkGray,
                                Color.White
                            )
                        ) {
                            Text("Del", fontFamily = akony, fontSize = 10.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            var total = 0
            cartItems.forEach { id -> total += catalog.find { product -> product.id == id }?.price
                ?: 0 }
            Text(
                "SUM: $total rub",
                fontSize = 20.sp,
                fontFamily = akony,
                color = Color.White,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}
