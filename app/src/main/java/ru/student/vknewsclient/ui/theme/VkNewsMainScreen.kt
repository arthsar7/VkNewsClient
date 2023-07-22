package ru.student.vknewsclient.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.student.vknewsclient.domain.FeedPost
import ru.student.vknewsclient.domain.NavigationItem
import ru.student.vknewsclient.domain.StatType

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val feedPost = viewModel.feedPost.observeAsState(FeedPost())
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                val selectedItemPos = remember { mutableStateOf(0) }
                items.forEachIndexed { index, it ->
                    NavigationBarItem(
                        selected = selectedItemPos.value == index,
                        onClick = { selectedItemPos.value = index },
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
        PostCard(
            modifier = Modifier.padding(it),
            feedPost = feedPost.value,
            onViewsClickListener = viewModel::updateCount,
            onLikesClickListener = viewModel::updateCount,
            onCommentsClickListener = viewModel::updateCount,
            onSharesClickListener = viewModel::updateCount
        )
    }

}