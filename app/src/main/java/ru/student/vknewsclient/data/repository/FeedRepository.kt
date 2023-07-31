package ru.student.vknewsclient.data.repository

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import ru.student.vknewsclient.data.mapper.FeedMapper
import ru.student.vknewsclient.data.network.ApiFactory
import ru.student.vknewsclient.domain.StatItem
import ru.student.vknewsclient.domain.StatType
import ru.student.vknewsclient.presentation.comments.Comment
import ru.student.vknewsclient.presentation.news.FeedPost

class FeedRepository(
    application: Application
) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService

    private val mapper = FeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<FeedPost> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty()) {
            return feedPosts
        }

        val response =
            if (startFrom == null) apiService.loadRecommendations(getAccessToken())
            else apiService.loadRecommendations(getAccessToken(), startFrom)
        nextFrom = response.feedContent.nextFrom
        val posts = mapper.mapResponseToPost(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    suspend fun getComments(feedPost: FeedPost): List<Comment> {
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapResponseToComments(response)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response =
            if (feedPost.isLiked)
                apiService.deleteLike(
                    token = getAccessToken(),
                    ownerId = feedPost.communityId,
                    itemId = feedPost.id
                )
            else apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        val newLikesCount = response.likes.count
        val newStats = feedPost.stats.toMutableList().apply {
            removeIf { it.type == StatType.LIKES }
            add(StatItem(StatType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(stats = newStats, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }

    suspend fun ignorePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }


    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("VKAccessToken is null")
    }
}