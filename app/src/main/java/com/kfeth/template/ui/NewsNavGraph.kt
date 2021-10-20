package com.kfeth.template.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kfeth.template.ui.Destinations.ArticleDetails
import com.kfeth.template.ui.Destinations.ArticleDetailsArgs.ArticleId
import com.kfeth.template.ui.Destinations.Home
import com.kfeth.template.ui.details.DetailsScreen
import com.kfeth.template.ui.home.HomeScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Destinations {
    const val Home = "home"
    const val ArticleDetails = "articleDetails"

    object ArticleDetailsArgs {
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
                onArticleTap = { articleId ->
                    val encodedUrl = URLEncoder.encode(articleId, StandardCharsets.UTF_8.toString())
                    navController.navigate("$ArticleDetails/$encodedUrl")
                }
            )
        }

        composable(
            route = "$ArticleDetails/{$ArticleId}",
            arguments = listOf(navArgument(ArticleId) { type = NavType.StringType })
        ) { backStackEntry ->
            DetailsScreen(
                articleId = backStackEntry.arguments?.getString(ArticleId) ?: "",
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}
