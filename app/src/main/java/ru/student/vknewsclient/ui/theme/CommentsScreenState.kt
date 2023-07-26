package ru.student.vknewsclient.ui.theme

import ru.student.vknewsclient.domain.Comment
import ru.student.vknewsclient.domain.FeedPost

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<Comment>
    ) : CommentsScreenState()

}
