package com.example.pamn_museum.screens

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
import com.example.pamn_museum.R
import com.example.pamn_museum.ui.theme.MockPost
import java.util.*
import kotlin.random.Random


@Composable
fun homeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        FeedScreen()
    }
}

//David
val postList = listOf(
    R.drawable.monalisa, R.drawable.picasso, R.drawable.cuevaverdes, R.drawable.lanzarote,
    R.drawable.angel, R.drawable.nocheestrellada
)

//David
val commonNames = listOf(
    "PAMN_Museum", "Romen", "Deivid", "AleSama", "Chanote", "Manise"
)

//David
val mockPosts = List(100) {
    val uid = UUID.randomUUID().toString()
    val userImage = R.drawable.logo
    val username = "PAMN MUSEUM"//commonNames[Random.nextInt(commonNames.size)]
    val image = postList[Random.nextInt(postList.size)]
    val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Pellentesque ex nisi, consequat non sem ut, sodales condimentum felis." +
            "Quisque ac justo vitae est pretium pretium at ut nulla. Etiam elementum nec massa et finibus. " +
            "Mauris sit amet nunc ut quam auctor malesuada et ut orci. " +
            "Nullam dapibus orci id ipsum venenatis, in fermentum felis porttitor. " +
            "Sed bibendum nibh lorem, at elementum velit tempus vitae. " +
            "Etiam vulputate iaculis nulla, id porttitor nibh volutpat id. " +
            "Aliquam et risus ut ante sagittis hendrerit."
    val likes = Random.nextInt(1000)
    val comments = Random.nextInt(100)
    MockPost(uid, userImage, username, image, description, likes, comments)
}

@Composable
fun FeedScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
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
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = Color.LightGray,
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
        Image(
            painter = painterResource(id = post.image),
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

