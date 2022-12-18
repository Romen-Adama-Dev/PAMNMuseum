package com.example.pamn_museum.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
    object Scan : BottomBarScreen(
        route = "scan",
        title = "Scan",
        icon = Icons.Default.Menu
    )
    object Ticket : BottomBarScreen(
        route = "ticket",
        title = "Ticket",
        icon =  Icons.Default.Email
    )
}

sealed class LoginApp(
    val routeLogin: String,
    val titleLogin: String
) {
    object Login : LoginApp(
        routeLogin = "login",
        titleLogin = "Login",
    )
    object Reset : LoginApp(
        routeLogin = "reset",
        titleLogin = "Reset",
    )
    object Register : LoginApp(
        routeLogin = "register",
        titleLogin = "Register",
    )
}
