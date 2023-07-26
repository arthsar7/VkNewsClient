package ru.student.vknewsclient.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.student.vknewsclient.domain.Comment
import ru.student.vknewsclient.domain.FeedPost
import ru.student.vknewsclient.domain.StatItem

class FeedViewModel : ViewModel() {

    private val _screenState: MutableLiveData<FeedScreenState> = MutableLiveData(
        FeedScreenState.Posts(
            List(10) {
                FeedPost(id = it, communityName = it.toString())
            }
        )
    )
    val screenState: LiveData<FeedScreenState> = _screenState

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
