package ru.student.vknewsclient.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.student.vknewsclient.domain.NavigationItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile
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
        val feedPost = viewModel.feedPost.observeAsState(listOf())
        LazyColumn(contentPadding = it, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(
                items = feedPost.value,
                key = { _, item -> item.id }
            ) { _, model ->
                val state = rememberDismissState()
                if (state.isDismissed(DismissDirection.EndToStart) || state.isDismissed(
                        DismissDirection.StartToEnd
                    )
                ) {
                    viewModel.removePost(model)
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = state,
                    background = {
                        Box(modifier = Modifier.background(Color.White))
                    },
                    dismissContent = {
                        PostCard(
                            feedPost = model,
                            onViewsClickListener = { statItem ->
                                viewModel.updateCount(model, statItem)
                            },
                            onSharesClickListener = { statItem ->
                                viewModel.updateCount(model, statItem)
                            },
                            onCommentsClickListener = { statItem ->
                                viewModel.updateCount(model, statItem)
                            },
                            onLikesClickListener = { statItem ->
                                viewModel.updateCount(model, statItem)
                            },
                        )
                    },
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart)
                )

            }
        }
//        PostCard(
//            modifier = Modifier.padding(it),
//            feedPost = feedPost.value,
//            onViewsClickListener = viewModel::updateCount,
//            onLikesClickListener = viewModel::updateCount,
//            onCommentsClickListener = viewModel::updateCount,
//            onSharesClickListener = viewModel::updateCount
//        )
    }

}