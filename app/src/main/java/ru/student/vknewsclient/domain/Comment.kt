package ru.student.vknewsclient.domain

import ru.student.vknewsclient.R

data class Comment(
    val id: Int,
    val authorName: String = "Author",
    val avatarId: Int = R.drawable.post_comunity_thumbnail,
    val commentText: String = "Long comment text",
    val date: String = "14:00"
) {
}