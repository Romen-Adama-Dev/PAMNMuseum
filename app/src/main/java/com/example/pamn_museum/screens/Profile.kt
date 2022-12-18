package com.example.pamn_museum.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pamn_museum.R
import com.example.pamn_museum.ui.theme.Grey200
import com.example.pamn_museum.ui.theme.White500
import com.google.android.gms.tasks.Tasks

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class userData(
    var name:String,
    var phone:String,
    var email:String
)

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier
        .background(Grey200)
        .fillMaxHeight()){
        val notification = rememberSaveable { mutableStateOf("") }
        if (notification.value.isNotEmpty()) {
            Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
            notification.value = ""
        }

        var name by rememberSaveable { mutableStateOf("default name") }
        var username by rememberSaveable { mutableStateOf("default username") }
        var bio by rememberSaveable { mutableStateOf("default bio") }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
                .background(Grey200)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Sign out",
                    modifier = Modifier.clickable {
                        FirebaseAuth.getInstance().signOut() ;

                        navController.navigate("login_page")

                    })

            }

            ProfileImage()

            //.........................Spacer
            Spacer(modifier = Modifier.height(60.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Bienvenido/a ${FirebaseAuth.getInstance().currentUser?.email}" ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = White500)

            }


        }





    }
}

@Composable
fun InfoProfile(){

}

@Composable
fun ProfileImage() {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        //.........................Spacer
        Spacer(modifier = Modifier.height(3.dp))

        Text(text = "Change profile picture")
    }
}