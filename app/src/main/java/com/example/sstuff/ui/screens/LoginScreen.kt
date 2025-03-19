package com.example.sstuff.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sstuff.akony
import com.example.sstuff.data.UserPreferences
import com.example.sstuff.ui.components.LoginForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val userDataFlow = UserPreferences.getUserData(context)
    val user by userDataFlow.collectAsState(initial = null to null)
    val scope = rememberCoroutineScope()

    Text(
        text = "Auth", fontSize = 30.sp, fontFamily = akony, color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        textAlign = TextAlign.Center
    )
    if (user.first != null && user.second != null) {
        // Пользователь авторизован
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                Modifier
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        brush = Brush.radialGradient(colors = listOf(
                            Color.Black.copy(0.15f),
                            Color.Black.copy(0.3f),
                        ),
                            radius = 300f,
                        )
                    )
            ) {
                Text("Welcome, ${user.second}!", fontSize = 26.sp, fontFamily = akony, modifier = Modifier.padding(40.dp))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(Color.White, Color.Black, Color.DarkGray, Color.White),
                onClick = {
                scope.launch(Dispatchers.IO) {
                    UserPreferences.clearUserData(context)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text("Log out", fontSize = 18.sp, fontFamily = akony, color = Color.Black)
            }
        }
    } else {
        LoginForm()
    }

}