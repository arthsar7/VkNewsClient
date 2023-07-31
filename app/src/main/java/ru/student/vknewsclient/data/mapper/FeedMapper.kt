package ru.student.vknewsclient.data.mapper

import ru.student.vknewsclient.data.model.CommentsResponseDto
import ru.student.vknewsclient.data.model.FeedResponseDto
import ru.student.vknewsclient.domain.StatItem
import ru.student.vknewsclient.domain.StatType
import ru.student.vknewsclient.presentation.comments.Comment
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
                communityId = post.sourceId,
                publicationDate = mapTimestampToDate(post.date),
                avatarURL = group.photoUrl,
                contentText = post.text,
                contentURL = ((post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url
                    ?: continue).toString()),
                stats = listOf(
                    StatItem(type = StatType.VIEWS, count = post.viewsDto.count),
                    StatItem(type = StatType.COMMENTS, count = post.comments.count),
                    StatItem(type = StatType.SHARES, count = post.repostsDto.count),
                    StatItem(type = StatType.LIKES, count = post.likes.count)
                ),
                isLiked = post.likes.userLikes == 1
            )


            result.add(feedPost)
        }
        return result.toList()
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * MILLIS_IN_SECONDS)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }

    fun mapResponseToComments(responseDto: CommentsResponseDto): List<Comment> {
        val result = mutableListOf<Comment>()
        val comments = responseDto.content.comments
        val profiles = responseDto.content.profiles
        for (comment in comments) {
            val profile = profiles.firstOrNull() { it.id == comment.authorId } ?: continue
            val newComment = Comment(
                id = comment.id,
                authorName = profile.firstName + " " + profile.lastName,
                avatarUrl = profile.avatarUrl,
                commentText = comment.text,
                date = mapTimestampToDate(comment.date)
            )
            result.add(newComment)
        }
        return result.toList()
    }

    private companion object {
        private const val MILLIS_IN_SECONDS = 1000
    }
}