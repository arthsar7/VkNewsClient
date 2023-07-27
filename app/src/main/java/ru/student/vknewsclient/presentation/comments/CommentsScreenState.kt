package ru.student.vknewsclient.presentation.comments

import ru.student.vknewsclient.presentation.news.FeedPost

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<Comment>
    ) : CommentsScreenState()

}
