package ru.plovotok.githubtest.ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.plovotok.domain.models.GHRepository
import ru.plovotok.domain.models.Owner
import ru.plovotok.githubtest.ui.theme.Typography
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun RepositoryItem(
    repository: GHRepository,
    onRepositoryClick: (GHRepository) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .clickable {
                onRepositoryClick(repository)
            }
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        OwnerInfo(owner = repository.owner)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = repository.name,
            style = Typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(2.dp))
        repository.description?.let {
            Text(
                text = it,
                style = Typography.bodyMedium,
                color = Color.Black,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        AdditionalInfo(repository = repository)
        Spacer(modifier = Modifier.height(4.dp))
        UpdatedAtBlock(updatedAt = repository.updatedAt)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun OwnerInfo(owner: Owner) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = owner.avatarUrl,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = owner.username,
            style = Typography.bodyMedium,
            color = Color.Gray,
        )
    }
}

@Composable
private fun AdditionalInfo(repository: GHRepository) {
    Icons.Default.Star
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        StarsBlock(stars = repository.stars)
        Spacer(modifier = Modifier.width(8.dp))
        repository.language?.let { LanguagesBlock(langs = it) }
    }
}

@Composable
private fun StarsBlock(stars: Int) {
    val text = remember {
        if (stars < 1000) "$stars" else "${String.format("%.1f", stars.toDouble()/1000)}k"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color(0xFFFF8400)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = Typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
private fun LanguagesBlock(langs: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .background(Color.Cyan, CircleShape)
            .size(8.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = langs,
            style = Typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
private fun UpdatedAtBlock(updatedAt: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Updated at:", style = Typography.bodyMedium, color = Color.Black)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = LocalDateTime.parse(updatedAt, pattern).format(outerPatter), style = Typography.bodyMedium, color = Color.Gray)
    }
}

private val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
private val outerPatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")