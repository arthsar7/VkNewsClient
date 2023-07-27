package ru.student.vknewsclient.presentation.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.student.vknewsclient.presentation.news.FeedPost

@Composable
fun CommentsScreen(
    onBackPressedListener: () -> Unit,
    feedPost: FeedPost
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(feedPost)
    )
    val screenState = viewModel.screenState
        .observeAsState(CommentsScreenState.Initial)
    val currentState = screenState.value
    if (currentState !is CommentsScreenState.Comments) return
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comments for FeedPost id: ${currentState.feedPost.id} ${currentState.feedPost.communityName}") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressedListener() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = it
        ) {
            itemsIndexed(
                items = currentState.comments,
                key = { _, comment -> comment.id }
            ) { _, comment ->
                PostComment(comment)
            }

        }

    }
}

@Preview
@Composable
fun PostComment(
    comment: Comment = Comment(0)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = comment.avatarId),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(top = 4.dp)
        )
        Column {
            Text(
                text = "${comment.authorName} id:${comment.id}",
                modifier = Modifier.padding(start = 4.dp),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = comment.commentText,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp, top = 0.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = comment.date,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 4.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(Modifier.height(4.dp))
            Divider(color = Color.Black)
        }
    }
}