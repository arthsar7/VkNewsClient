package ru.student.vknewsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
) {
    val viewModel: FeedViewModel = viewModel()
    val screenState = viewModel.screenState
        .observeAsState(FeedScreenState.Posts(listOf()))
    when (val currentState = screenState.value) {
        is FeedScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                feedPosts = currentState.posts,
                viewModel = viewModel,
                onCommentsClickListener = onCommentsClickListener
            )
        }
        is FeedScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    paddingValues: PaddingValues,
    feedPosts: List<FeedPost>,
    viewModel: FeedViewModel,
    onCommentsClickListener: (FeedPost) -> Unit
) {
    LazyColumn(contentPadding = paddingValues, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(
            items = feedPosts,
            key = { _, item -> item.id }
        ) { _, feedPost ->
            val state = rememberDismissState()
            if (state.isDismissed(DismissDirection.StartToEnd)) {
                viewModel.removePost(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = state,
                background = {
                    Box(modifier = Modifier.background(Color.White))
                },
                dismissContent = {
                    PostCard(
                        feedPost = feedPost,
                        onViewsClickListener = { statItem ->
                            viewModel.updateCount(feedPost, statItem)
                        },
                        onSharesClickListener = { statItem ->
                            viewModel.updateCount(feedPost, statItem)
                        },
                        onCommentsClickListener = { onCommentsClickListener(feedPost) },
                        onLikesClickListener = { statItem ->
                            viewModel.updateCount(feedPost, statItem)
                        },
                    )
                },
                directions = setOf(DismissDirection.StartToEnd)
            )
        }
    }
}