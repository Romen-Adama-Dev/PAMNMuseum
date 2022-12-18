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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pamn_museum.R
import com.example.pamn_museum.components.Visibility
import com.example.pamn_museum.components.VisibilityOff
import com.example.pamn_museum.ui.theme.colorLogo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class regUser(
    var email: String,
    var password: String,
    var passwordCheck: String,
    var name: String,
    var phone: String
)

@Composable
fun RegisterPage(navController: NavController) {
    var regUser = regUser("","", "", "", "")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = colorLogo,
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_museo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                )
            Column(
                modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //.........................Spacer
                Spacer(modifier = Modifier.height(30.dp))
                //.........................Text: title
                Text(
                    text = "Create An Account",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                RegisterName(regUser)
                Spacer(modifier = Modifier.padding(3.dp))
                RegisterPhone(regUser)
                Spacer(modifier = Modifier.padding(3.dp))
                RegisterEmail(regUser)
                Spacer(modifier = Modifier.padding(3.dp))
                RegisterPassword(regUser)
                Spacer(modifier = Modifier.padding(3.dp))
                RegisterPasswordConfirm(regUser)
                val gradientColor = listOf(Color(0xFFFFFFFF), Color(0xFFB8B8B8))
                val cornerRadius = 16.dp
                Spacer(modifier = Modifier.padding(10.dp))

                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Create An Account",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),
                    regUser = regUser
                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = {
                    navController.navigate("login_page"){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Text(
                        text = "Sign In",
                        color = Color.White,
                        letterSpacing = 1.sp,
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
                        color = Color.White,
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
    regUser: regUser
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
                  if(regUser.password == regUser.passwordCheck){
                      Firebase.auth.createUserWithEmailAndPassword(regUser.email, regUser.password)
                          .addOnCompleteListener{task ->
                              if (task.isSuccessful) {
                                  Log.i("registro", "register success")
                                  val user = hashMapOf(
                                      "name" to regUser.name,
                                      "email" to regUser.email,
                                      "phone" to regUser.phone
                                  )
                                  Firebase.firestore.collection("users")
                                      .add(user)
                                      .addOnSuccessListener { documentReference ->
                                          Log.d("registro", "Usuario con id: ${documentReference.id} y nombre ${regUser.name} añadido")
                                      }
                              } else {
                                  Log.e("registro", "register error",task.exception)
                              }

                          }
                  }else{
                  }

        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
        ),
        shape = RoundedCornerShape(cornerRadius),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

//name
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterName(regUser: regUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it;
                        regUser.name= it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Name") },
        placeholder = { Text(text = "Name") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
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


//phone
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterPhone(regUser: regUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it;
                        regUser.phone = it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Phone",
            ) },
        placeholder = { Text(text = "Phone") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Phone
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


//email id
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterEmail(regUser: regUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it;
                        regUser.email= it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Email Address",
            ) },
        placeholder = { Text(text = "Email Address") },
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
fun RegisterPassword(regUser: regUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    OutlinedTextField(
        value = password,
        onValueChange = { password = it;
                       regUser.password = it },
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Enter Password") },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Visibility else VisibilityOff
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}

//password confirm
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterPasswordConfirm(regUser: regUser) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    OutlinedTextField(
        value = password,
        onValueChange = { password = it;
                        regUser.passwordCheck = it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Confirm Password",
            ) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Visibility else VisibilityOff
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
}