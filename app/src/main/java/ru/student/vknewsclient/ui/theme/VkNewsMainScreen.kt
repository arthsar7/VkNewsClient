package ru.student.vknewsclient.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.student.vknewsclient.navigation.AppNavGraph
import ru.student.vknewsclient.navigation.rememberNavigationState
import ru.student.vknewsclient.presentation.comments.CommentsScreen
import ru.student.vknewsclient.presentation.favorite.FavoriteScreen
import ru.student.vknewsclient.presentation.main.NavigationItem
import ru.student.vknewsclient.presentation.news.NewsScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry = navigationState.navHostController
                    .currentBackStackEntryAsState()
                val items = listOf(
                    NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile
                )
                items.forEach {
                    val selected =
                        navBackStackEntry.value?.destination?.hierarchy?.any {
                                destination ->
                            it.screen.route == destination.route
                        } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = { if(!selected) navigationState.navigate(it.screen.route) },
                        icon = {
                            Icon(
                                imageVector = it.icon, contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(id = it.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }
        },
    ) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                NewsScreen(paddingValues = it) {
                    navigationState.navigateToComments(it)
                }
            },
            favouriteScreenContent = {
                FavoriteScreen(paddingValues = it)
            },
            profileScreenContent = { Text(text = "Prof") },
            commentsScreenContent = {
                CommentsScreen(
                    onBackPressedListener = {
                        navigationState.navHostController.popBackStack()
                    },
                    feedPost = it
                )
            }
        )
    }
}