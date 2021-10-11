package com.kfeth.template.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kfeth.template.ui.Destinations.HOME
import com.kfeth.template.ui.home.HomeScreen

object Destinations {
    const val HOME = "home"
}

@Composable
fun NewsNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HOME
    ) {
        composable(HOME) {
            HomeScreen()
        }
    }
}