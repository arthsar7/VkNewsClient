package ru.student.vknewsclient.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.student.vknewsclient.domain.FeedPost
import ru.student.vknewsclient.domain.StatItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun updateCount(item: StatItem){
        val oldStats = _feedPost.value?.stats ?: throw IllegalStateException()
        val newStats = oldStats.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    return@replaceAll oldItem.copy(count = oldItem.count + 1)
                }
                return@replaceAll oldItem
            }
        }
        _feedPost.value = _feedPost.value?.copy(stats = newStats) ?: throw IllegalStateException()
    }
}