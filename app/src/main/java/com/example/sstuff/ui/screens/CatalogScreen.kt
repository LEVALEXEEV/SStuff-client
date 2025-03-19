package com.example.sstuff.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sstuff.akony
import com.example.sstuff.data.UserPreferences
import com.example.sstuff.data.models.Product
import com.example.sstuff.ui.components.ProductCard

@Composable
fun CatalogScreen(navController: NavController) {
    val context = LocalContext.current
    var products by rememberSaveable { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        UserPreferences.getCatalog(context).collect { catalog ->
            products = catalog
        }
    }

    Text(text = "CATALOG", fontSize = 30.sp, fontFamily = akony, color = Color.White, modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), textAlign = TextAlign.Center)
    LazyColumn(modifier = Modifier.padding(16.dp).padding(top = 20.dp)) {
        items(products) { product ->
            ProductCard(product) {
                navController.navigate("product/${product.id}")
            }
        }
    }
}