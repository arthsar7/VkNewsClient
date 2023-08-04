package ru.student.vknewsclient.domain.entity

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
@Immutable
@Parcelize
data class FeedPost(
    val id: Long,
    val communityName: String = "/dev/null",
    val communityId: Long,
    val publicationDate: String = "14:00",
    val avatarURL: String,
    val contentText: String,
    val contentURL: String?,
    val stats: List<StatItem>,
    val isLiked: Boolean,
    val isFavorite: Boolean
): Parcelable {
    companion object {
        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, FeedPost::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key, value)
            }

        }
    }
}
