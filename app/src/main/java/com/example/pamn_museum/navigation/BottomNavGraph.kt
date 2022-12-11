package com.example.pamn_museum.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pamn_museum.screens.ProfileScreen
import com.example.pamn_museum.screens.SettingsScreen
import com.example.pamn_museum.screens.homeScreen
import com.example.pamn_museum.screens.qrScreen

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
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}