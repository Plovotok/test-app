package ru.plovotok.githubtest.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.http.content.Version
import ru.plovotok.githubtest.BuildConfig
import ru.plovotok.githubtest.ui.theme.Typography
import ru.plovotok.githubtest.util.openUrl

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Version()
        Spacer(modifier = Modifier.height(4.dp))
        Author(author = "plovotok")
    }
}

@Composable
private fun Version() {
    Row {
        Text(text = "Version:", style = Typography.bodyLarge, color = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = BuildConfig.VERSION_NAME,
            style = Typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun Author(author: String) {
    val context = LocalContext.current
    Row {
        Text(text = "Author:", style = Typography.bodyLarge, color = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "@$author",
            style = Typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                context.openUrl("https://github.com/$author")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    AboutScreen()
}