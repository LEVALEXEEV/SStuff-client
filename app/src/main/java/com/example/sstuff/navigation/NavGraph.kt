package com.example.sstuff.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sstuff.ui.screens.CartScreen
import com.example.sstuff.ui.screens.CatalogScreen
import com.example.sstuff.ui.screens.FrontScreen
import com.example.sstuff.ui.screens.LoginScreen
import com.example.sstuff.ui.screens.ProductScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController,
        startDestination = "front",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("front") { FrontScreen(navController) }
        composable("catalog") { CatalogScreen(navController) }
        composable("product/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductScreen(productId = productId, navController = navController)
        }
        composable("cart") { CartScreen() }
        composable("login") { LoginScreen() }
    }
}