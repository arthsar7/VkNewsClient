package ru.student.vknewsclient.data.mapper

import android.util.Log
import ru.student.vknewsclient.data.model.CommentsResponseDto
import ru.student.vknewsclient.data.model.FaveContentDto
import ru.student.vknewsclient.data.model.FeedContentDto
import ru.student.vknewsclient.domain.entity.Comment
import ru.student.vknewsclient.domain.entity.FeedPost
import ru.student.vknewsclient.domain.entity.StatItem
import ru.student.vknewsclient.domain.entity.StatType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue

class FeedMapper @Inject constructor() {

    fun mapResponseToPost(content: FeedContentDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = content.posts
        Log.d("Feed", content.toString())
        val groups = content.groups
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
                isLiked = post.likes.userLikes == 1,
                isFavorite = post.isFavorite
            )

            result.add(feedPost)
        }
        return result.toList()
    }

    fun mapResponseToPost(content: FaveContentDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        val posts = content.posts
        val groups = content.groups
        Log.d("Feed", "comment $posts")
        for (postContent in posts) {
            val post = postContent.post
            val group = groups.find { it.id == post.sourceId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                communityId = post.sourceId,
                publicationDate = mapTimestampToDate(post.date),
                avatarURL = group.photoUrl,
                contentText = post.text,
                contentURL = (post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url.toString()),
                stats = listOf(
                    @Suppress("USELESS_ELVIS", "UNNECESSARY_SAFE_CALL")
                    StatItem(type = StatType.VIEWS, count =  post.views?.count ?: 0),
                    StatItem(type = StatType.COMMENTS, count = post.comments.count),
                    StatItem(type = StatType.SHARES, count = post.reposts.count),
                    StatItem(type = StatType.LIKES, count = post.likes.count)
                ),
                isLiked = post.likes.userLikes == 1,
                isFavorite = post.isFavorite
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
            val profile = profiles.firstOrNull { it.id == comment.authorId } ?: continue
            val newComment = Comment(
                id = comment.id,
                authorName = profile.firstName + " " + profile.lastName,
                avatarUrl = profile.avatarUrl,
                commentText = comment.text,
                date = mapTimestampToDate(comment.date),
            )
            result.add(newComment)
        }
        return result.toList()
    }

    private companion object {
        private const val MILLIS_IN_SECONDS = 1000
    }
}