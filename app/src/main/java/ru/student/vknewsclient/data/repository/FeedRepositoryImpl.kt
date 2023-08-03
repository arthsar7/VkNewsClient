package ru.student.vknewsclient.data.repository

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.student.vknewsclient.data.extensions.mergeWith
import ru.student.vknewsclient.data.mapper.FeedMapper
import ru.student.vknewsclient.data.network.ApiService
import ru.student.vknewsclient.domain.entity.AuthState
import ru.student.vknewsclient.domain.entity.Comment
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.entity.StatItem
import ru.student.vknewsclient.domain.entity.StatType
import ru.student.vknewsclient.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val storage: VKPreferencesKeyValueStorage,
    private val mapper: FeedMapper
) : FeedRepository {

    private val token
        get() = VKAccessToken.restore(storage)


    private val coroutineScope = CoroutineScope(Dispatchers.Default)


    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val loadedListFlow = flow {
        loadNextData()
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) apiService.loadRecommendations(getAccessToken())
            else apiService.loadRecommendations(getAccessToken(), startFrom)
            nextFrom = response.feedContent.nextFrom
            val posts = mapper.mapResponseToPost(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }
        .retry {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }

    private val recommendations = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            if (token?.isValid == true) {
                emit(AuthState.Authorized)
                return@collect
            }
            emit(AuthState.NotAuthorized)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> {
        return authStateFlow
    }

    override fun getRecommendations(): StateFlow<List<FeedPost>> {
        return recommendations
    }

    override fun getComments(feedPost: FeedPost): StateFlow<List<Comment>> {
        return flow {
            val response = apiService.getComments(
                token = getAccessToken(), ownerId = feedPost.communityId, postId = feedPost.id
            )
            emit(mapper.mapResponseToComments(response))
        }.retry {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) apiService.deleteLike(
            token = getAccessToken(), ownerId = feedPost.communityId, itemId = feedPost.id
        )
        else apiService.addLike(
            token = getAccessToken(), ownerId = feedPost.communityId, itemId = feedPost.id
        )
        val newLikesCount = response.likes.count
        val newStats = feedPost.stats.toMutableList().apply {
            removeIf { it.type == StatType.LIKES }
            add(StatItem(StatType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(stats = newStats, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun ignorePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }


    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("VKAccessToken is null")
    }

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}