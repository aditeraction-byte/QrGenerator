package com.example.qrgenerator.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp




@Composable
fun QrCard(
    title: String,
    url: String,
    qrBitmap: ImageBitmap,
    onEdit: () -> Unit,
    onStats: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { AppText("Delete QR", style = MaterialTheme.typography.titleMedium) },
            text = { AppText("Are you sure you want to delete this QR code?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) { AppText("Delete", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { AppText("Cancel") }
            }
        )
    }

    val gradientBorder = Brush.horizontalGradient(
        colors = listOf(Color(0xFF5A9BFF), Color(0xFF6A11CB))
    )

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        gradientBorder = gradientBorder,
        cornerRadius = 16.dp,
        elevation = 2.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        bitmap = qrBitmap,
                        contentDescription = "QR code for $title",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        AppText(
                            text = if (title.length > 25) title.take(25) + "…" else title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF212121),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        AppText(
                            text = url,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF424242),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF5A9BFF))
                        Spacer(Modifier.width(4.dp))
                        AppText("Editar", color = Color(0xFF5A9BFF))
                    }
                    TextButton(onClick = onStats) {
                        Icon(Icons.Default.Star, contentDescription = "Stats", tint = Color(0xFF6A11CB))
                        Spacer(Modifier.width(4.dp))
                        AppText("Stats", color = Color(0xFF6A11CB))
                    }
                }
            }

            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete QR",
                    tint = Color(0xFFE53935)
                )
            }
        }
    }
}