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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pamn_museum.R
import com.example.pamn_museum.ui.theme.colorLogo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class email(
    var mail: String
)

@Composable
fun ResetPage(navController: NavController) {
    var email = email("")
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
                painter = painterResource(id = R.drawable.logo_login),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth(),
            )
            Column(
                modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //.........................Spacer
                Spacer(modifier = Modifier.height(180.dp))
                //.........................Text: title
                Text(
                    text = "Reset Password",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth().size(40.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                ResetEmailID(email)
                Spacer(modifier = Modifier.padding(3.dp))
                val gradientColor = listOf(Color(0xFFFFFFFF), Color(0xFFB8B8B8))
                val cornerRadius = 16.dp
                Spacer(modifier = Modifier.padding(10.dp))
                GradientButtonReset(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Submit",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),
                    email = email
                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = {
                    navController.navigate("register_page"){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }) {
                    Text(
                        text = "Sign Up?",
                        color = Color.White,
                        letterSpacing = 1.sp,
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

//...........................................................................
@Composable
private fun GradientButtonReset(
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape,
    email: email
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = {
            Firebase.auth.sendPasswordResetEmail(email.mail)
                .addOnCompleteListener {t ->
                    Log.i("reset","correo mandado")
                }
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
        ),
        shape = RoundedCornerShape(cornerRadius)
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

//email id
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResetEmailID(email: email) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it;
                        email.mail = it},
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Enter Registered Email",
            ) },
        placeholder = { Text(text = "Enter Registered Email") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
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
