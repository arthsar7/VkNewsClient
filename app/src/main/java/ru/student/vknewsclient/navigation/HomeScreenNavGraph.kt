package ru.student.vknewsclient.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.student.vknewsclient.presentation.news.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(
    homeScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route,
        builder = {
            composable(Screen.NewsFeed.route) {
                homeScreenContent()
            }

            composable(
                route = Screen.Comments.route,
                arguments = listOf(
                    navArgument(Screen.Comments.COMMENT_KEY_POST) {
                        type = FeedPost.NavigationType
                    }
                )
            ) {
                val feedPost =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.arguments?.getParcelable(
                            Screen.Comments.COMMENT_KEY_POST,
                            FeedPost::class.java
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        it.arguments?.getParcelable(Screen.Comments.COMMENT_KEY_POST)
                    } ?: throw IllegalStateException("Empty args")
                commentsScreenContent(feedPost)
            }
        }
    )
}