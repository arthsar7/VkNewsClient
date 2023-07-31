package ru.student.vknewsclient.presentation.news

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    object Loading : FeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : FeedScreenState()

}
