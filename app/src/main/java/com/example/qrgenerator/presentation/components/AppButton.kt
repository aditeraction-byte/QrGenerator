package com.example.qrgenerator.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
/**
 * A customizable button that fills the width of its container.
 *
 * @param onClick Lambda executed when the button is clicked.
 * @param text Text to display inside the button.
 * @param modifier Modifier for styling and layout.
 * @param containerColor Background color of the button.
 * @param contentColor Color of the text/content.
 * @param shape Shape of the button corners.
 * @param enabled Whether the button is enabled or disabled.
 */
@Composable
fun AppButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = RoundedCornerShape(12.dp),
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        enabled = enabled
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}