package com.example.sstuff.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.example.sstuff.ui.components.AnimatedGradientBackground
import com.example.sstuff.R
import com.example.sstuff.akony
import com.example.sstuff.data.UserPreferences
import com.example.sstuff.data.authUser
import com.example.sstuff.data.getCatalog
import com.example.sstuff.navigation.NavGraph
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf("Front", "Catalog", "Cart", "Login")
    val selectedItem = remember { mutableStateOf(items[0]) }
    val context = LocalContext.current
    val isTokenChecked by rememberSaveable { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        val fetchedCatalog = getCatalog()
        UserPreferences.saveCatalog(context, fetchedCatalog)
        if (!isTokenChecked) {
            UserPreferences.getUserData(context).collect { user ->
                val token = user.first
                if (token != null) {
                    val success = authUser(token)
                    if (!success) {
                        scope.launch {
                            UserPreferences.clearUserData(context)
                        }
                    }
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Text("Menu", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            ModalDrawerSheet {
                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item, fontSize = 20.sp, fontFamily = akony) },
                        selected = selectedItem.value == item,
                        onClick = {
                            selectedItem.value = item
                            navController.navigate(item)
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.icon),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp).clickable {
                                navController.navigate("front")
                            }
                        )
                    },
                    actions = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            AnimatedGradientBackground()
            NavGraph(navController, paddingValues)
        }
    }
}
