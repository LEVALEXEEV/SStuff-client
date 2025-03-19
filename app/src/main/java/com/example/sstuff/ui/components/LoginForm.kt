package com.example.sstuff.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sstuff.R
import com.example.sstuff.akony
import com.example.sstuff.data.UserPreferences
import com.example.sstuff.data.loginUser
import com.example.sstuff.data.models.User
import com.example.sstuff.data.registerUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginForm() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var isSignUpMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        // Вертикальный переключатель Sign In / Sign Up
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            if (isSignUpMode) Color(0xFF3A3A3A) else Color.Gray,
                            if (isSignUpMode) Color.Gray else Color(0xFF3A3A3A)
                        )
                    )
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Кнопка "Sign In"
                Button(
                    onClick = { isSignUpMode = false },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 14.sp,
                        color = if (isSignUpMode) Color.Gray else Color.White,
                        modifier = Modifier.padding(4.dp),
                        fontFamily = akony
                    )
                }

                // Кнопка "Sign Up"
                Button(
                    onClick = { isSignUpMode = true },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor =  Color.Transparent)
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 14.sp,
                        color = if (isSignUpMode) Color.White else Color.Gray,
                        modifier = Modifier.padding(4.dp),
                        fontFamily = akony
                    )
                }
            }
        }

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
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Login", color = Color.White, fontFamily = akony, fontSize = 12.sp) },
                    textStyle = TextStyle(fontFamily = akony, fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        unfocusedTextColor = Color.Yellow,
                        focusedTextColor = Color.Yellow
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.White, fontFamily = akony, fontSize = 12.sp) },
                    textStyle = TextStyle(fontFamily = akony),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            if (isPasswordVisible) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_on),
                                    contentDescription = "Toggle Password Visibility",
                                    modifier = Modifier.size(25.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_off),
                                    contentDescription = "Toggle Password Visibility",
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        unfocusedTextColor = Color.Yellow,
                        focusedTextColor = Color.Yellow
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Кнопка для входа или регистрации
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                    scope.launch(Dispatchers.IO) {
                        val success = if (isSignUpMode) {
                            registerUser(User(username, password)) // Регистрация
                        } else {
                            val token = loginUser(User(username, password)) // Вход
                            scope.launch {
                                if (token != null) {
                                    UserPreferences.saveUserData(context, token = token, username = username)
                                }
                            }
                            token != null
                        }
                        withContext(Dispatchers.Main) {
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, if (isSignUpMode) "Registration Successful" else "Login Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, if (isSignUpMode) "Registration Failed" else "Invalid Credentials", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Please, fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(Color.White, Color.Black, Color.DarkGray, Color.White),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Black)
            } else {
                Text("Submit", fontSize = 18.sp, fontFamily = akony, color = Color.Black)
            }
        }
    }
}