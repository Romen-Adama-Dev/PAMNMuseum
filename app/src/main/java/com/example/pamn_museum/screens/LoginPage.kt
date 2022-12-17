package com.example.pamn_museum.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pamn_museum.MainScreen
import com.example.pamn_museum.R
import com.example.pamn_museum.components.Visibility
import com.example.pamn_museum.components.VisibilityOff
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class LoginUser(
    var email: String,
    var password: String
)

@Composable
fun LoginPage(navController: NavController) {
    var loginUser = LoginUser("","")

    if(FirebaseAuth.getInstance().currentUser != null){
        homeScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.DarkGray,
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //.........................Spacer
                Spacer(modifier = Modifier.height(50.dp))
                //.........................Text: title
                Text(
                    text = "Sign In",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                SimpleOutlinedTextFieldSample(loginUser)

                Spacer(modifier = Modifier.padding(3.dp))
                SimpleOutlinedPasswordTextField(loginUser)

                val gradientColor = listOf(Color(0xFF9E0000), Color(0xFFFF0000))
                val cornerRadius = 16.dp
                Spacer(modifier = Modifier.padding(10.dp))
                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Login",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),
                    loginUser = loginUser,

                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = {

                    navController.navigate("register_page"){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Text(
                        text = "Create An Account",
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                TextButton(onClick = {
                    navController.navigate("reset_page"){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Text(
                        text = "Reset Password",
                        letterSpacing = 1.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}
//...........................................................................
@Composable
private fun GradientButton(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape,
    loginUser: LoginUser,
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            Log.i("login", "${loginUser.email} ${loginUser.password}")

            Firebase.auth.signInWithEmailAndPassword(loginUser.email, loginUser.password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful) {
                        //ADAMA: Navegar a home
                        Log.i("login", "login success")
                    } else {
                        Log.e("login", "login error",task.exception)
                      //ADAMA: si el login falla por que la combinacion es incorrecta, mostrar mensaje de error
                    }

              }


        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(),
        shape = RoundedCornerShape(cornerRadius),
        //enabled = loginUser.email.length > 0 && loginUser.password.length > 0,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

//email id
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleOutlinedTextFieldSample(loginUser: LoginUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it;
            loginUser.email = it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Name or Email Address") },
        placeholder = { Text(text = "Name or Email Address") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}

//password
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleOutlinedPasswordTextField(loginUser: LoginUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    OutlinedTextField(
        value = password,
        onValueChange = { password = it;
            loginUser.password = it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Enter Password") },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        //  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Visibility else VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // Codigo david Firebase
            }
        )
    )
}