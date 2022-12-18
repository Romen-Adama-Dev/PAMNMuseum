package com.example.pamn_museum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pamn_museum.navigation.LoginNav
import com.example.pamn_museum.screens.ProfileScreen
import com.example.pamn_museum.screens.homeScreen
import com.example.pamn_museum.ui.theme.PAMN_MuseumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PAMN_MuseumTheme {
                LoginNav()
            }
        }
    }
}

