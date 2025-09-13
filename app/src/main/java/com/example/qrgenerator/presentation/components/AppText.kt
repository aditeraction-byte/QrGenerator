package com.example.qrgenerator.presentation.components

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun AppText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}