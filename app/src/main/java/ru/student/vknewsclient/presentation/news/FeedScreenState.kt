package ru.student.vknewsclient.presentation.news

import ru.student.vknewsclient.domain.entity.FeedPost

sealed class FeedScreenState {

    object Initial : FeedScreenState()

    object Loading : FeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : FeedScreenState()

}
