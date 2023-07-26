package ru.student.vknewsclient.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.student.vknewsclient.domain.FeedPost

class CommentsViewModelFactory(private val feedPost: FeedPost): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}