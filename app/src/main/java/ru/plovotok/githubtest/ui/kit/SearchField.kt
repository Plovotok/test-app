package ru.plovotok.githubtest.ui.kit

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import ru.plovotok.githubtest.ui.theme.GithubTestTheme
import ru.plovotok.githubtest.ui.theme.Typography

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = "Enter something", style = Typography.bodyLarge, color = Color.Gray)
        },
        trailingIcon = {
            IconButton(onClick = { onSearch(value) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        },
        keyboardActions = KeyboardActions(onSearch = { onSearch(value) }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchFieldPreview() {
    GithubTestTheme {
        SearchField("", {}, {})
    }
}