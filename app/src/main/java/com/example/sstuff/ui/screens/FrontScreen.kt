package com.example.sstuff.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sstuff.akony
import com.example.sstuff.ui.components.FrontScreenSlider
import com.example.sstuff.ui.components.PoliceTapeButton

@Composable
fun FrontScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Real STUFF",
            fontSize = 30.sp,
            fontFamily = akony,
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )

        FrontScreenSlider(
            listOf("front1" to "product/1", "front2" to "catalog", "front3" to "cart"),
            navController = navController,
        )

        Spacer(modifier = Modifier.height(70.dp))

        // Полицейские ленты с бегущей строкой
        PoliceTapeButton("auth   ", navController, "login", 6f)
        Spacer(modifier = Modifier.height(40.dp))
        PoliceTapeButton("ctlg", navController, "catalog", -8f)
        Spacer(modifier = Modifier.height(40.dp))
        PoliceTapeButton("cart   ", navController, "cart", 7f)
    }
}
