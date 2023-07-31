package ru.student.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.student.vknewsclient.data.repository.FeedRepository
import ru.student.vknewsclient.presentation.news.FeedPost

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost
) : AndroidViewModel(application) {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = FeedRepository(application)

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val comments = repository.getComments(feedPost)
            _screenState.value =
                CommentsScreenState.Comments(feedPost = feedPost, comments = comments)
        }
    }
}