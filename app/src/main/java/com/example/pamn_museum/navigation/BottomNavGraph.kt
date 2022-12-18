package com.example.pamn_museum.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pamn_museum.MainScreen
import com.example.pamn_museum.screens.*

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Scan.route) {
            qrScreen()
        }
        composable(route = BottomBarScreen.Home.route){
            homeScreen()
        }
        composable(route = BottomBarScreen.Ticket.route) {
            Ticket()
        }
    }
}

@Composable
fun LoginNav(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_page", builder = {
        composable("login_page", content = { LoginPage(navController = navController) })
        composable("register_page", content = { RegisterPage(navController = navController) })
        composable("reset_page", content = { ResetPage(navController = navController) })
        composable("main_page", content = { MainScreen(navController = navController) })
    })
}