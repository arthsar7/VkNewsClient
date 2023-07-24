package ru.student.vknewsclient.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.student.vknewsclient.domain.FeedPost
import ru.student.vknewsclient.domain.StatItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(
        List(10) {
            FeedPost(id = it, communityName = it.toString())
        }
    )
    val feedPost: LiveData<List<FeedPost>> = _feedPost

    fun updateCount(feedPost: FeedPost, item: StatItem) {
        val feedPosts = _feedPost.value?.toMutableList() ?: mutableListOf()
        feedPosts.replaceAll {
            if (it.id == feedPost.id) {
                return@replaceAll updatedPost(it, item)
            }
            return@replaceAll it
        }
        _feedPost.value = feedPosts
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
        val posts = _feedPost.value?.toMutableList() ?: mutableListOf()
        posts.remove(feedPost)
        _feedPost.value = posts
    }
}
