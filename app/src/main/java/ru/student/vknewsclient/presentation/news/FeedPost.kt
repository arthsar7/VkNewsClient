package ru.student.vknewsclient.presentation.news

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.student.vknewsclient.R
import ru.student.vknewsclient.domain.StatItem
import ru.student.vknewsclient.domain.StatType

@Parcelize
data class FeedPost(
    val id: Int = 0,
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
): Parcelable {
    companion object {
        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, FeedPost::class.java)
                } else {
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
