package com.kfeth.template.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kfeth.template.ui.Destinations.Details
import com.kfeth.template.ui.Destinations.DetailsArgs.ArticleId
import com.kfeth.template.ui.Destinations.Home
import com.kfeth.template.ui.details.DetailsScreen
import com.kfeth.template.ui.home.HomeScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Destinations {
    const val Home = "home"
    const val Details = "details"

    object DetailsArgs {
        const val ArticleId = "articleId"
    }
}

@Composable
fun NewsNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable(Home) {
            HomeScreen(
                onClickListItem = { articleUrl ->
                    val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("$Details/$encodedUrl")
                }
            )
        }

        composable(
            route = "$Details/{$ArticleId}",
            arguments = listOf(navArgument(ArticleId) { type = NavType.StringType })
        ) { backStackEntry ->
            DetailsScreen(
                articleId = backStackEntry.arguments?.getString(ArticleId) ?: "",
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
