package ru.student.vknewsclient.domain

data class StatItem(
    val type: StatType,
    val count: Int = 0
)

enum class StatType{
    VIEWS, COMMENTS, SHARES, LIKES
}
