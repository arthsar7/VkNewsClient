package ru.student.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.student.vknewsclient.data.repository.FeedRepository

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val _screenState: MutableLiveData<FeedScreenState> = MutableLiveData(FeedScreenState.Initial)
    val screenState: LiveData<FeedScreenState> = _screenState
    private val repository = FeedRepository(application)

    init {
        _screenState.value = FeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            _screenState.value = FeedScreenState.Posts(repository.loadRecommendations())
        }
    }


    fun loadNextRecommendations() {
        _screenState.value = FeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadRecommendations()
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _screenState.value = FeedScreenState.Posts(repository.feedPosts)
        }
    }


    fun removePost(feedPost: FeedPost) {

        viewModelScope.launch {
            repository.ignorePost(feedPost)
            _screenState.value = FeedScreenState.Posts(repository.feedPosts)
        }
    }

}
