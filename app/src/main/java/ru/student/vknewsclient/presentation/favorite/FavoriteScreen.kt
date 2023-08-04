package ru.student.vknewsclient.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.presentation.getApplicationComponent
import ru.student.vknewsclient.presentation.news.PostCard

@Composable
fun FavoriteScreen(
    paddingValues: PaddingValues
) {
    val viewModel: FavoriteViewModel =
        viewModel(factory = getApplicationComponent().getViewModelFactory())
    val screenState = viewModel.screenState
        .collectAsState(FavoriteScreenState.Initial)

    FeedScreenState(
        screenState = screenState,
        paddingValues = paddingValues,
        viewModel = viewModel
    )
}

@Composable
private fun FeedScreenState(
    screenState: State<FavoriteScreenState>,
    paddingValues: PaddingValues,
    viewModel: FavoriteViewModel,
) {
    when (val currentState = screenState.value) {
        is FavoriteScreenState.Posts -> {
            FeedPosts(
                paddingValues = paddingValues,
                feedPosts = currentState.posts,
                viewModel = viewModel
            )
        }

        is FavoriteScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is FavoriteScreenState.Empty -> {
            EmptyScreen()
        }

        is FavoriteScreenState.Initial -> {

        }
    }
}
@Preview(showBackground = true)
@Composable
private fun EmptyScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "У Вас нет избранного",
        )
    }
}

@Composable
private fun FeedPosts(
    paddingValues: PaddingValues,
    feedPosts: List<FeedPost>,
    viewModel: FavoriteViewModel
) {
    LazyColumn(contentPadding = paddingValues, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(
            items = feedPosts,
            key = { _, item -> item.id }
        ) { _, feedPost ->

            PostCard(
                feedPost = feedPost,
                onSharesClickListener = { },
                onCommentsClickListener = { },
                onLikesClickListener = { },
                onFaveClickListener = { viewModel.changeFaveStatus(feedPost) }
            )
        }
    }
}