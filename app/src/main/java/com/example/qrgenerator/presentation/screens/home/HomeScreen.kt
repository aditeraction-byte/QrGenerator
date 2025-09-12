package com.example.qrgenerator.presentation.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.qrgenerator.domain.model.QrDomain
import com.example.qrgenerator.utils.helpers.QrHelper
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCreateQr: () -> Unit,
    onQrDetail: (String) -> Unit,
    onQrStats: (String) -> Unit,
    reloadTrigger: Boolean
) {
    val uiState by viewModel.uiState.collectAsState()
    val softBlue = Color(0xFF5A9BFF) // azul más suave

    LaunchedEffect(reloadTrigger) { viewModel.loadQrs() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My QR App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = softBlue,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateQr,
                containerColor = softBlue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create QR")
            }
        },
        containerColor = Color.White
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (uiState) {
                is HomeUIState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = softBlue
                )

                is HomeUIState.Error -> Text(
                    text = (uiState as HomeUIState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )

                is HomeUIState.Success -> {
                    val list = (uiState as HomeUIState.Success).qrList
                    if (list.isEmpty()) {
                        Text(
                            text = "No QR codes found. Create one!",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(list) { qr ->
                                val qrBitmap = remember(qr.id) {
                                    QrHelper.generateQrBitmap(
                                        content = qr.redirectUrl,
                                        fgColorHex = qr.fgColor,
                                        bgColorHex = qr.bgColor,
                                        size = 150
                                    ).asImageBitmap()
                                }

                                QrCard(
                                    title = qr.title.ifEmpty { "QR ${qr.id.take(6)}" },
                                    url = qr.redirectUrl,
                                    qrBitmap = qrBitmap,
                                    onEdit = { onQrDetail(qr.id) },
                                    onStats = { onQrStats(qr.id) },
                                    onDelete = { viewModel.deleteQr(qr.id) } // 👈 acá lo pasamos
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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
            title = { Text("Delete QR") },
            text = { Text("Are you sure you want to delete this QR code?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
            .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // QR a la izquierda
                Image(
                    bitmap = qrBitmap,
                    contentDescription = "QR code for $title",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Column con title, url y botones
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = if (title.length > 25) title.take(25) + "…" else title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF212121)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = url,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF424242)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TextButton(onClick = onEdit) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF5A9BFF))
                            Spacer(Modifier.width(4.dp))
                            Text("Editar", color = Color(0xFF5A9BFF))
                        }
                        TextButton(onClick = onStats) {
                            Icon(Icons.Default.Star, contentDescription = "Analytics", tint = Color(0xFF6A11CB))
                            Spacer(Modifier.width(4.dp))
                            Text("Analytics", color = Color(0xFF6A11CB))
                        }
                    }
                }
            }

            // Botón de borrar en esquina superior derecha
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