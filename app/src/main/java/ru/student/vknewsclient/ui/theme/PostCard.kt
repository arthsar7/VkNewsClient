package ru.student.vknewsclient.ui.theme

import androidx.compose.foundation.Image
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

@Composable
fun PostCard() {
    val colorScheme = MaterialTheme.colorScheme
    Card(
        colors = CardDefaults.cardColors(colorScheme.background)
    ) {
        Header()
        Spacer(modifier = Modifier.height(4.dp))
        Content()
        Spacer(modifier = Modifier.height(4.dp))
        Stats()
    }


}

@Composable
private fun Content() {
    Text(
        text = "Lorem ipsum dolor sit amet",
        modifier = Modifier.padding(8.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Image(
        painter = painterResource(id = R.drawable.post_content_image),
        contentDescription = "post image",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun Stats() {
    Row(
        modifier = Modifier
    ) {
        Row(
            Modifier
                .padding(start = 2.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatIcon("916", R.drawable.ic_views_count)
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatIcon("7", R.drawable.ic_share)
            StatIcon("8", R.drawable.ic_comment)
            StatIcon("23", R.drawable.ic_like)
        }
    }
}

@Composable
private fun Header() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.post_comunity_thumbnail),
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
                    text = "Title", color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "14:00", color = MaterialTheme.colorScheme.onSecondary
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
private fun StatIcon(text: String, drawableId: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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


@Preview
@Composable
private fun PreviewLight() {
    VkNewsClientTheme(darkTheme = false) {
        PostCard()
    }
}

@Preview
@Composable
private fun PreviewDark() {
    VkNewsClientTheme(darkTheme = true) {
        PostCard()
    }
}