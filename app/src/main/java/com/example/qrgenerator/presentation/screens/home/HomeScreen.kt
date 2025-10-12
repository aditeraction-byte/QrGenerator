package com.example.qrgenerator.presentation.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import com.example.qrgenerator.presentation.components.AppScaffold
import com.example.qrgenerator.presentation.components.AppText
import com.example.qrgenerator.presentation.components.AppTopBar
import com.example.qrgenerator.presentation.components.QrCard
import com.example.qrgenerator.utils.helpers.QrHelper



/**
 * Composable responsible for displaying the Home screen of the app.
 *
 * Shows a welcome message, QR code list, loading/error states, and
 * a floating action button for creating new QR codes.
 *
 * @param viewModel [HomeViewModel] used to fetch user info and QR list.
 * @param onCreateQr Callback invoked when the user taps the "New QR" FAB.
 * @param onQrDetail Callback invoked when the user wants to view/edit a specific QR.
 * @param onQrStats Callback invoked when the user wants to view statistics for a QR.
 * @param reloadTrigger Boolean used to trigger reloading the QR list when changed.
 * @param onLogout Callback invoked after the user logs out.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCreateQr: () -> Unit,
    onQrDetail: (String) -> Unit,
    onQrStats: (String) -> Unit,
    reloadTrigger: Boolean,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val user by viewModel.user.collectAsState()
    val softBlue = Color(0xFF5A9BFF)
    val displayName = user?.email?.substringBefore("@") ?: "Usuario"

    // Reload QR list whenever reloadTrigger changes
    LaunchedEffect(reloadTrigger) { viewModel.loadQrs() }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Welcome, $displayName",
                showLogout = true,
                onLogout = { viewModel.logout { onLogout() } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateQr,
                containerColor = softBlue,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New QR",
                    tint = Color.White
                )
            }
        }
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

                is HomeUIState.Error -> AppText(
                    text = (uiState as HomeUIState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )

                is HomeUIState.Success -> {
                    val list = (uiState as HomeUIState.Success).qrList
                    if (list.isEmpty()) {
                        AppText(
                            text = "No QR codes found. Create one!",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(list) { qr ->
                                // Generate QR bitmap for display
                                val qrBitmap = remember(qr.id) {
                                    QrHelper.generateQrBitmap(
                                        content = qr.shortLink,
                                        fgColorHex = qr.fgColor,
                                        bgColorHex = qr.bgColor,
                                        size = 150
                                    ).asImageBitmap()
                                }

                                // Display each QR in a card with actions
                                QrCard(
                                    title = qr.title.ifEmpty { "QR ${qr.id.take(6)}" },
                                    url = qr.redirectUrl,
                                    qrBitmap = qrBitmap,
                                    onEdit = { onQrDetail(qr.id) },
                                    onStats = { onQrStats(qr.id) },
                                    onDelete = { viewModel.deleteQr(qr.id) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
