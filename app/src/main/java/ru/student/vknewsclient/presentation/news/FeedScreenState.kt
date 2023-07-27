package ru.student.vknewsclient.presentation.news

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    data class Posts(val posts: List<FeedPost>) : FeedScreenState()

}
