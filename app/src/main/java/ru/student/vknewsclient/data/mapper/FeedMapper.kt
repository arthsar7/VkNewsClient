package ru.student.vknewsclient.data.mapper

import ru.student.vknewsclient.data.model.FeedResponseDto
import ru.student.vknewsclient.domain.StatItem
import ru.student.vknewsclient.domain.StatType
import ru.student.vknewsclient.presentation.news.FeedPost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class FeedMapper {

    fun mapResponseToPost(response: FeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = response.feedContent.posts
        val groups = response.feedContent.groups
        for (post in posts) {
            val group = groups.find { post.sourceId.absoluteValue == it.id } ?: break
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date * MILLIS_IN_SECONDS),
                avatarURL = group.photoUrl,
                contentText = post.text,
                contentURL = ((post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url ?: continue).toString()),
                stats = listOf(
                    StatItem(type = StatType.VIEWS, count = post.viewsDto.count),
                    StatItem(type = StatType.COMMENTS, count = post.comments.count),
                    StatItem(type = StatType.SHARES, count = post.repostsDto.count),
                    StatItem(type = StatType.LIKES, count = post.likes.count)
                ),
                isFavorite = post.isFavorite
            )


            result.add(feedPost)
        }
        return result
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
    private companion object {
        private const val MILLIS_IN_SECONDS = 1000
    }
}