package com.example.ktorchatingapp.presentation.login.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyEditText(
    modifier: Modifier,
    hint: String,
    value: String,
    change: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = change,
        modifier = modifier,
        placeholder = {
            Text(text = hint)
        }
    )
}