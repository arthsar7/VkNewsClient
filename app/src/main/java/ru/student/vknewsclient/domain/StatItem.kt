package ru.student.vknewsclient.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatItem(
    val type: StatType,
    val count: Int = 0
): Parcelable

enum class StatType{
    VIEWS, COMMENTS, SHARES, LIKES
}
