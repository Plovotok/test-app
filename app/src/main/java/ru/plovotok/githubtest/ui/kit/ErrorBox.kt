package ru.plovotok.githubtest.ui.kit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.plovotok.domain.NetworkException
import ru.plovotok.githubtest.ui.theme.Typography

@Composable
fun ErrorBox(cause: Throwable, onRetry: () -> Unit) {
    val message = when {
        cause is NetworkException -> cause.message
        else -> "Something went wrong"
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = Typography.bodyMedium, color = Color.Red)
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = onRetry, shape = RoundedCornerShape(8.dp)) {
            Text(text = "Try again", style = Typography.bodyMedium)
        }
    }
}