package ru.student.vknewsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.presentation.ViewModelFactory

@Composable
fun NewsScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    val viewModel: FeedViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState
        .collectAsState(FeedScreenState.Initial)
    when (val currentState = screenState.value) {
        is FeedScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                feedPosts = currentState.posts,
                viewModel = viewModel,
                onCommentsClickListener = onCommentsClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }

        is FeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
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
    onCommentsClickListener: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean
) {
    LazyColumn(contentPadding = paddingValues, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(
            items = feedPosts,
            key = { _, item -> item.id }
        ) { _, feedPost ->
            val state = rememberDismissState()
            if (state.isDismissed(DismissDirection.EndToStart)) {
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
                        onSharesClickListener = {
                        },
                        onCommentsClickListener = { onCommentsClickListener(feedPost) },
                    ) {
                        viewModel.changeLikeStatus(feedPost)
                    }
                },
                directions = setOf(DismissDirection.EndToStart)
            )
        }
        item {
            if(nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                )
                {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            else
            {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}