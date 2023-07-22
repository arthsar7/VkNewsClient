package ru.student.vknewsclient.domain

import ru.student.vknewsclient.R

data class FeedPost(
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet",
    val contentResId: Int = R.drawable.post_content_image,
    val stats: List<StatItem> = listOf(
        StatItem(type = StatType.VIEWS, 0),
        StatItem(type = StatType.SHARES, 0),
        StatItem(type = StatType.COMMENTS, 0),
        StatItem(type = StatType.LIKES, 0),
    )
)
