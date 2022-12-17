package com.example.pamn_museum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pamn_museum.navigation.LoginNav
import com.example.pamn_museum.ui.theme.PAMN_MuseumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PAMN_MuseumTheme {
                LoginNav()
                //MainScreen()
            }
        }
    }
}
