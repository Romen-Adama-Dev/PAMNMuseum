package com.example.pamn_museum.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pamn_museum.R
import com.example.pamn_museum.ui.theme.Grey200
import com.example.pamn_museum.ui.theme.MockPost
import com.example.pamn_museum.ui.theme.White500
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

val db = Firebase.firestore

fun loadPosts(): MutableList<MockPost> {
    var posts =  mutableListOf<MockPost>()

    db.collection("posts")
        .get()
        .addOnSuccessListener { result ->
            for (document in result){
                var description = document.data.get("description") as String
                var likes = (document.data.get("likes") as Long).toInt()
                var comments = (document.data.get("comments") as Long).toInt()
                var image = document.data.get("image") as String

                //Datos de usuario Random
                val uid = UUID.randomUUID().toString()
                val userImage = R.drawable.logo_white
                val username = "  PAMN MUSEUM"
                var post= MockPost(uid, userImage, username, image, description, likes, comments)
                posts.add(post)

                Log.i("firebase", "${post.description}  ${post.image} ${post.likes} ${post.comments}")

            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    return posts
}

@Composable
fun homeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey200),
        contentAlignment = Alignment.Center
    ) {
        loadPosts()
        FeedScreen()
    }
}

val mockPosts = loadPosts()

@Composable
fun FeedScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalDivider()
    }
    LazyColumn { items(mockPosts) { post -> PostItem(post = post) } }
}

@Composable
fun RoundImageCard(
    image: Int, modifier: Modifier = Modifier
        .padding(8.dp)
        .size(64.dp)
) {
    Card(shape = CircleShape, modifier = modifier) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Fit,

        )
    }
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = White500,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun PostItem(post: MockPost) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.DarkGray)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RoundImageCard(
                image = post.userImage,
                Modifier
                    .size(48.dp)
                    .padding(4.dp)
            )
            Text(text = post.userName, fontWeight = FontWeight.Bold)
        }
        AsyncImage(
            model = post.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Text(text = post.description, modifier = Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(text = "${post.likes} likes", modifier = Modifier.padding(start = 8.dp))
        }
        Text(
            text = "${post.comments} comments",
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    FeedScreen()
}

