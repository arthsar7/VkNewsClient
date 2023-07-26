package ru.student.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.student.vknewsclient.domain.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {

        homeScreenNavGraph(homeScreenContent, commentsScreenContent)

        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
    }
}

