package com.example.qrgenerator.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    gradientBorder: Brush? = null,
    useGradientBackground: Boolean = false,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 4.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (gradientBorder != null) Modifier.border(2.dp, gradientBorder, RoundedCornerShape(cornerRadius))
                else Modifier
            )
            .clip(RoundedCornerShape(cornerRadius)),
        colors = CardDefaults.cardColors(
            containerColor = if (useGradientBackground && gradientBorder != null) Color.Transparent else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Box(
            modifier = if (useGradientBackground && gradientBorder != null) Modifier.background(gradientBorder) else Modifier,
            content = content
        )
    }
}