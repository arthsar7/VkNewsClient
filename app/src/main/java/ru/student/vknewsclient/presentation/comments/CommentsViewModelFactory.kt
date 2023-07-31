package ru.student.vknewsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.student.vknewsclient.presentation.news.FeedPost

class CommentsViewModelFactory(
    private val application: Application,
    private val feedPost: FeedPost
    ): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(
            application = application,
            feedPost = feedPost
        ) as T
    }
}