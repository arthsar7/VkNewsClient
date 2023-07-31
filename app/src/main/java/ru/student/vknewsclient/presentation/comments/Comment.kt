package ru.student.vknewsclient.presentation.comments

data class Comment(
    val id: Long,
    val authorName: String = "Author",
    val avatarUrl: String,
    val commentText: String = "Long comment text",
    val date: String = "14:00"
) {
}