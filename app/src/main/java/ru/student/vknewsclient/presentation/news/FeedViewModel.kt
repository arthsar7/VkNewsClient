package ru.student.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.launch
import ru.student.vknewsclient.data.mapper.FeedMapper
import ru.student.vknewsclient.data.network.ApiFactory
import ru.student.vknewsclient.domain.StatItem

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState: MutableLiveData<FeedScreenState> = MutableLiveData(FeedScreenState.Initial)
    val screenState: LiveData<FeedScreenState> = _screenState
    private val mapper = FeedMapper()

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val storage = VKPreferencesKeyValueStorage(getApplication())
            val token = VKAccessToken.restore(storage) ?: return@launch
            val response = ApiFactory.apiService.loadRecommendation(token.accessToken)
            val feedPosts = mapper.mapResponseToPost(response)
            Log.d("PZDC", feedPosts[0].contentURL.toString())
            _screenState.value = FeedScreenState.Posts(feedPosts)
        }
    }
    fun updateCount(feedPost: FeedPost, item: StatItem) {
        val feedPosts = (_screenState.value as? FeedScreenState.Posts)
            ?.posts?.toMutableList() ?: return
        feedPosts.replaceAll {
            if (it.id == feedPost.id) {
                return@replaceAll updatedPost(it, item)
            }
            return@replaceAll it
        }
        _screenState.value = FeedScreenState.Posts(feedPosts)
    }

    private fun updatedPost(feedPost: FeedPost, item: StatItem): FeedPost {
        val oldStats = feedPost.stats
        val newStats = oldStats.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    return@replaceAll oldItem.copy(count = oldItem.count + 1)
                }
                return@replaceAll oldItem
            }
        }
        return feedPost.copy(stats = newStats)
    }

    fun removePost(feedPost: FeedPost) {
        val posts = (_screenState.value as? FeedScreenState.Posts)?.posts
            ?.toMutableList() ?: return
        posts.remove(feedPost)
        _screenState.value = FeedScreenState.Posts(posts)
    }

}
