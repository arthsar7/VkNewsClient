package ru.student.vknewsclient.ui.theme

import ru.student.vknewsclient.domain.FeedPost

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    data class Posts(val posts: List<FeedPost>) : FeedScreenState()

}
