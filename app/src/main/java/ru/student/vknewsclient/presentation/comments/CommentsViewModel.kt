package ru.student.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.usecases.GetCommentsUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    feedPost: FeedPost,
    getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {


    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}