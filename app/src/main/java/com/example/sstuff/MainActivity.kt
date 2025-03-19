package com.example.sstuff

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.sstuff.ui.screens.ShopApp

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopApp()
        }
    }
}
/*
@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopApp() {
    val navController = rememberNavController()
    val cart = remember { mutableStateListOf<Product>() }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf("Front", "Catalog", "Cart", "Login")
    val selectedItem = remember { mutableStateOf(items[0]) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Text("Меню", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        },
        gesturesEnabled = true,
        scrimColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .clickable(
                                    enabled = true,
                                    onClick = {
                                        navController.navigate("front")
                                    }
                                )
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Filled.Menu, "Меню")
                        }
                    }
                )
            }
        ) { paddingValues ->
            AnimatedGradientBackground()
            NavHost(
                navController,
                startDestination = "front",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("front") { FrontScreen(navController) }
                composable("catalog") { CatalogScreen(navController) }
                composable("product/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toInt()
                    val product = products.find { it.id == productId }
                    product?.let { ProductScreen(it, cart) }
                }
                composable("cart") { CartScreen(cart) { cart.remove(it) } }
                composable("login") { LoginScreen(navController) }
            }
        }
    }
}


@Composable
fun CatalogScreen(navController: NavController) {
    Text(text = "CATALOG", fontSize = 30.sp, fontFamily = akony, color = Color.White, modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), textAlign = TextAlign.Center)
    LazyColumn(modifier = Modifier.padding(16.dp).padding(top = 20.dp)) {
        items(products) { product ->
            ProductCard(product) {
                navController.navigate("product/${product.id}")
            }
        }
    }
}

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

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun PoliceTapeButton(text: String, navController: NavController, route: String, rotation: Float) {
    val shift = if (text.startsWith("cart")) 90 else 0
    val direction = if (rotation < 0) -1 else 1
    val initialValue = if (rotation < 0) 70f else 50f + shift
    val targetValue = if (rotation < 0) -290f else -320f + shift
    val infiniteTransition = rememberInfiniteTransition()
    val offsetX by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .width(600.dp)
            .height(40.dp)
            .graphicsLayer(rotationZ = rotation, scaleX = 1.5f, scaleY = 1.5f)
            .clickable { navController.navigate(route) }
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            .border(1.dp, Color.Black)
            .dashedBorder(
                Color.Black, shape = RectangleShape,
                strokeWidth = 4.dp, gapLength = 16.dp, dashLength = 16.dp, cap = StrokeCap.Square
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .offset(x = (direction * offsetX).dp)
        ) {
            // Дублируем текст для бесшовной анимации
            for (i in 0..2) {
                Text(
                    text = "⚠ $text", // Повторяем текст для плавного эффекта
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = akony,
                    modifier = Modifier
                        .graphicsLayer(translationX = offsetX + i * 300f) // Смещение второго блока текста
                )
            }
        }
    }
}






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
            Image(painter = painterResource(id = product.image), contentDescription = null,modifier = Modifier.size(300.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(product.description, fontSize = 12.sp, fontFamily = akony, color = Color.Gray)
                Text(product.name, fontSize = 20.sp, fontFamily = akony, modifier = Modifier.padding(vertical = 10.dp))
                Text("${product.price} rub", fontFamily = akony)
            }
        }
    }
}




@Composable
fun ProductScreen(product: Product, cart: MutableList<Product>) {
    val added = remember { mutableStateOf(cart.contains(product)) }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier
            .background(
                shape = RoundedCornerShape(50.dp),
                brush = Brush.radialGradient(colors = listOf(
                    Color.White,
                    Color.Black.copy(0.15f),
                    ),
                    radius = 600f,
                )
            )
        ){
            CustomSlider(listOf("nika_long_fw","nika_long_bc"))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(product.name, fontSize = 24.sp, fontFamily = akony, color = Color.White)
        Text("Price: ${product.price} rub", fontSize = 20.sp, fontFamily = akony, color = Color.White)
        Text(product.description, modifier = Modifier.padding(8.dp), fontFamily = akony, color = Color.White)
        Button(modifier = Modifier.fillMaxWidth(), colors = ButtonColors(Color.White, Color.Black, Color.DarkGray, Color.White), onClick = {
            if (added.value) {
                cart.remove(product)
            } else {
                cart.add(product)
            }
            added.value = !added.value
        }) {
            Text(if (added.value) "DEL" else "TO CART", fontFamily = akony, fontSize = 10.sp)
        }
    }
}




@Composable
fun CartScreen(cart: MutableList<Product>, onRemoveProduct: (Product) -> Unit) {
    Text("CART", fontSize = 30.sp, fontFamily = akony, color = Color.White, modifier = Modifier.fillMaxWidth().padding(top = 10.dp), textAlign = TextAlign.Center)
    Column(modifier = Modifier.padding(16.dp).padding(top = 20.dp)) {
        LazyColumn {
            items(cart) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${product.name} - ${product.price} руб.",
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f),
                        fontFamily = akony,
                        color = Color.White
                    )
                    Button(onClick = { onRemoveProduct(product) }, colors = ButtonColors(Color.White, Color.Black, Color.DarkGray, Color.White)) {
                        Text("Удалить", fontFamily = akony, fontSize = 10.sp)
                    }
                }
            }
        }
    Spacer(modifier = Modifier.height(16.dp))
    val total = cart.sumOf { it.price }
    Text("SUM: $total rub", fontSize = 20.sp, fontFamily = akony, color = Color.White, modifier = Modifier.padding(vertical = 10.dp))
    }

}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var isSignUpMode by remember { mutableStateOf(false) }  // Состояние для переключения режимов
    val tokenDataStore = remember { TokenDataStore(context) }

    Text(
        text = "Auth", fontSize = 30.sp, fontFamily = akony, color = Color.White,
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        textAlign = TextAlign.Center
    )

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

        Box(Modifier
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
                    coroutineScope.launch(Dispatchers.IO) {
                        val success = if (isSignUpMode) {
                            registerUser(username, password) // Регистрация
                        } else {
                            val token = loginUser(username, password) // Вход
                            coroutineScope.launch {
                                if (token != null) {
                                    tokenDataStore.saveToken(token)
                                }
                            }
                            token != null
                        }
                        withContext(Dispatchers.Main) {
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, if (isSignUpMode) "Registration Successful" else "Login Successful", Toast.LENGTH_SHORT).show()
                                if (!isSignUpMode) navController.navigate("front")
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
}*/


