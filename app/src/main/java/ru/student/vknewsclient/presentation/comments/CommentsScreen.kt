package ru.student.vknewsclient.presentation.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.student.vknewsclient.R
import ru.student.vknewsclient.domain.entity.Comment
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.presentation.getApplicationComponent

@Composable
fun CommentsScreen(
    onBackPressedListener: () -> Unit,
    feedPost: FeedPost,
) {
    val component = getApplicationComponent()
        .getCommentScreenComponentFactory()
        .create(feedPost)
    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState
        .collectAsState(CommentsScreenState.Initial)
    CommentsScreenContent(
        screenState = screenState,
        onBackPressedListener = onBackPressedListener
    )
}

@Composable
private fun CommentsScreenContent(
    screenState: State<CommentsScreenState>,
    onBackPressedListener: () -> Unit
) {
    val currentState = screenState.value
    if (currentState !is CommentsScreenState.Comments) return
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.commentsTitle)) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedListener() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            itemsIndexed(
                items = currentState.comments,
                key = { _, comment -> comment.id }
            ) { _, comment ->
                PostComment(comment)
            }

        }

    }
}

@Composable
fun PostComment(
    comment: Comment
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = comment.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .size(42.dp)
                .clip(CircleShape)
                .wrapContentSize()
        )
        Column {
            Text(
                text = "${comment.authorName} id:${comment.id}",
                modifier = Modifier.padding(start = 4.dp, top = 2.dp),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = comment.commentText,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = comment.date,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 4.dp, top = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(Modifier.height(4.dp))

            Divider(color = Color.Black)
        }
    }
}