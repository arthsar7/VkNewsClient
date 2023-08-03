package ru.student.vknewsclient.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.student.vknewsclient.domain.entity.AuthState
import ru.student.vknewsclient.domain.entity.Comment
import ru.student.vknewsclient.domain.entity.FeedPost

interface FeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>
    fun getRecommendations(): StateFlow<List<FeedPost>>
    fun getComments(feedPost: FeedPost): StateFlow<List<Comment>>

    suspend fun loadNextData()
    suspend fun checkAuthState()
    suspend fun ignorePost(feedPost: FeedPost)
    suspend fun changeLikeStatus(feedPost: FeedPost)

}