package ru.student.vknewsclient.domain.entity

data class Comment(
    val id: Long,
    val authorName: String = "Author",
    val avatarUrl: String,
    val commentText: String = "Long comment text",
    val date: String = "14:00"
) {
}