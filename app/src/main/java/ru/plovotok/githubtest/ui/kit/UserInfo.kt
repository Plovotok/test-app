package ru.plovotok.githubtest.ui.kit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.plovotok.domain.models.GHUser
import ru.plovotok.githubtest.ui.theme.Typography
import ru.plovotok.githubtest.util.mailTo
import ru.plovotok.githubtest.util.openUrl

@Composable
fun UserInfo(user: GHUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Header(imageUrl = user.avatarUrl, username = user.login, name = user.name)
        Spacer(modifier = Modifier.height(8.dp))
        user.bio?.let {
            BioInfo(bio = it)
            Spacer(modifier = Modifier.height(16.dp))
        }
        ContactInfo(user = user)
        Spacer(modifier = Modifier.height(8.dp))
        FollowInfo(followers = user.followers, followings = user.following)
    }
}

@Composable
private fun Header(imageUrl: String, username: String, name: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            name?.let {
                Text(
                    text = it,
                    style = Typography.titleLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = username,
                style = Typography.titleMedium,
                color = Color.Gray,
            )
        }

    }
}

@Composable
private fun BioInfo(bio: String) {
    Text(text = bio, style = Typography.bodyMedium, color = Color.Black)
}

@Composable
private fun ContactInfo(user: GHUser) {
    Column {
        if (!user.blog.isNullOrEmpty()) {
            BlogInfo(blog = user.blog!!)
            Spacer(modifier = Modifier.height(8.dp))
        }
        user.email?.let {
            EmailInfo(email = it)
            Spacer(modifier = Modifier.height(8.dp))
        }
        user.twitterUsername?.let {
            TwitterInfo(twitterUsername = it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun BlogInfo(blog: String) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "blog:",
            style = Typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = blog,
            style = Typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                context.openUrl(blog)
            }
        )
    }
}

@Composable
private fun EmailInfo(email: String) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "email:",
            style = Typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = email,
            style = Typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                context.mailTo(arrayOf(email), "")
            }
        )
    }
}

@Composable
private fun TwitterInfo(twitterUsername: String) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "twitter:",
            style = Typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "@$twitterUsername",
            style = Typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                context.openUrl("https://www.x.com/$twitterUsername")
            }
        )
    }
}

@Composable
private fun FollowInfo(followers: Int, followings: Int) {
    Column(
        modifier = Modifier.width(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = followers.toString(),
                style = Typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "followers",
                style = Typography.bodyMedium,
                color = Color.Gray,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = followings.toString(),
                style = Typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "followings",
                style = Typography.bodyMedium,
                color = Color.Gray,
            )
        }
    }
}

