package ru.student.vknewsclient.navigation

import android.net.Uri
import com.google.gson.Gson
import ru.student.vknewsclient.domain.entity.FeedPost

sealed class Screen(
    val route: String,
) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favourite : Screen(ROUTE_FAVOURITE)
    object Profile : Screen(ROUTE_PROFILE)
    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS) {
        const val COMMENT_KEY_POST = "post"
        private const val ROUTE_COMMENTS_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostJson = Gson().toJson(feedPost)
            return "$ROUTE_COMMENTS_FOR_ARGS/${feedPostJson.encode()}"
        }
    }
    private companion object {
        const val ROUTE_HOME = "home"
        //
        const val ROUTE_COMMENTS = "comments/{${Comments.COMMENT_KEY_POST}}"
        const val ROUTE_NEWS_FEED = "news_feed"
        //

        const val ROUTE_FAVOURITE = "favourite"

        const val ROUTE_PROFILE = "profile"
    }
}
fun String.encode(): String{
    return Uri.encode(this)
}