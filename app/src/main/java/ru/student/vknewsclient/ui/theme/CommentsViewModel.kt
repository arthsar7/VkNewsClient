package ru.student.vknewsclient.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.student.vknewsclient.domain.Comment
import ru.student.vknewsclient.domain.FeedPost
class CommentsViewModel(
    feedPost: FeedPost
) : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost) {
        val comments = MutableList(10) { Comment(id = it) }
        _screenState.value = CommentsScreenState.Comments(feedPost = feedPost, comments = comments)
    }
}