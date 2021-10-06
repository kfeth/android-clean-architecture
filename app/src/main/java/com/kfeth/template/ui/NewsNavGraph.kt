package com.kfeth.template.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kfeth.template.ui.Destinations.ARTICLE_LIST
import com.kfeth.template.ui.list.ListScreen

object Destinations {
    const val ARTICLE_LIST = "list"
}

@Composable
fun NewsNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ARTICLE_LIST
    ) {
        composable(ARTICLE_LIST) {
            ListScreen()
        }
    }
}