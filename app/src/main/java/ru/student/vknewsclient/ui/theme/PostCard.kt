package ru.student.vknewsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.student.vknewsclient.R
import ru.student.vknewsclient.domain.FeedPost
import ru.student.vknewsclient.domain.StatItem
import ru.student.vknewsclient.domain.StatType

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost = FeedPost(),
    onViewsClickListener: (StatItem) -> Unit,
    onSharesClickListener: (StatItem) -> Unit,
    onCommentsClickListener: (StatItem) -> Unit,
    onLikesClickListener: (StatItem) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(colorScheme.background)
    ) {
        Header(feedPost)
        Spacer(modifier = Modifier.height(4.dp))
        Content(feedPost)
        Spacer(modifier = Modifier.height(4.dp))
        Stats(
            feedPost = feedPost,
            onViewsClickListener = onViewsClickListener,
            onSharesClickListener = onSharesClickListener,
            onCommentsClickListener = onCommentsClickListener,
            onLikesClickListener = onLikesClickListener
        )

    }

}

@Composable
private fun Content(feedPost: FeedPost) {
    Text(
        text = feedPost.contentText,
        modifier = Modifier.padding(8.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Image(
        painter = painterResource(id = feedPost.contentResId),
        contentDescription = "post image",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun Stats(
    feedPost: FeedPost,
    onViewsClickListener: (StatItem) -> Unit,
    onSharesClickListener: (StatItem) -> Unit,
    onCommentsClickListener: (StatItem) -> Unit,
    onLikesClickListener: (StatItem) -> Unit
) {
    val stats = feedPost.stats
    Row(
        modifier = Modifier
    ) {
        Row(
            Modifier
                .padding(start = 2.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val viewsItem = stats.getItemByType(StatType.VIEWS)
            StatIcon(
                drawableId = R.drawable.ic_views_count,
                text = viewsItem.count.toString(),
                clickListener = {
                    onViewsClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = stats.getItemByType(StatType.SHARES)
            val commentsItem = stats.getItemByType(StatType.COMMENTS)
            val likesItem = stats.getItemByType(StatType.LIKES)
            StatIcon(
                drawableId = R.drawable.ic_share,
                text = sharesItem.count.toString(),
                clickListener = {
                    onSharesClickListener(sharesItem)
                }
            )
            StatIcon(
                drawableId = R.drawable.ic_comment,
                text = commentsItem.count.toString(),
                clickListener = {
                    onCommentsClickListener(commentsItem)
                }
            )
            StatIcon(
                drawableId = R.drawable.ic_like,
                text = likesItem.count.toString(),
                clickListener = {
                    onLikesClickListener(likesItem)
                }
            )
        }
    }
}

private fun List<StatItem>.getItemByType(type: StatType): StatItem {
    return find { it.type == type }!!
}

@Composable
private fun Header(feedPost: FeedPost) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = feedPost.avatarResId),
                contentDescription = "avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = feedPost.communityName, color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = feedPost.publicationDate, color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "more",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
private fun StatIcon(
    text: String,
    drawableId: Int,
    clickListener: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            clickListener()
        }
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onSecondary,
            imageVector = ImageVector.vectorResource(id = drawableId),
            contentDescription = null,
            modifier = Modifier.padding(end = 2.dp)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(2.dp)
        )
    }
}
