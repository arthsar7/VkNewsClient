package ru.student.vknewsclient.presentation.favorite

import ru.student.vknewsclient.domain.entity.FeedPost

sealed class FavoriteScreenState {

    object Initial : FavoriteScreenState()

    object Loading : FavoriteScreenState()

    object Empty : FavoriteScreenState()

    data class Posts(val posts: List<FeedPost>) : FavoriteScreenState()

}
