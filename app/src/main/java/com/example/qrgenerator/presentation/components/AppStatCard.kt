package com.example.qrgenerator.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AppStatCard(
    title: String,
    value: String,
    icon: ImageVector? = null,
    iconTint: Color = Color(0xFF7A4DCC),
    textColor: Color = Color(0xFF7A4DCC),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(36.dp)
                )
                AppSpacer(width = 16.dp)
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                AppText(text = title, color = Color.Gray)
                AppText(
                    text = value,
                    color = textColor
                )
            }
        }
    }
}