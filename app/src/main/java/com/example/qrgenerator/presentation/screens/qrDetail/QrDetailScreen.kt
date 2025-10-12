package com.example.qrgenerator.presentation.screens.qrDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.qrgenerator.presentation.components.AppButton
import com.example.qrgenerator.presentation.components.AppText
import com.example.qrgenerator.utils.helpers.QrHelper

/**
 * Composable for displaying and editing a specific QR code.
 * Shows a QR preview, editable title and redirect URL, and handles loading/error states.
 *
 * @param qrId The ID of the QR to display.
 * @param viewModel ViewModel handling QR loading and updates.
 * @param onBack Callback triggered when the user navigates back.
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrDetailsScreen(
    qrId: String,
    viewModel: QrDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Snackbar host for showing messages
    val snackbarHostState = remember { SnackbarHostState() }

    // Screen width to scale the QR preview
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Load the QR data when qrId changes
    LaunchedEffect(qrId) { viewModel.loadQr(qrId) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF7A4DCC), Color(0xFF5AA0FF))))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                // Show loading indicator
                is QrDetailsUIState.Loading -> CircularProgressIndicator(color = Color(0xFF5AA0FF))

                // Show error message
                is QrDetailsUIState.Error -> AppText(
                    text = (uiState as QrDetailsUIState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Show QR details
                is QrDetailsUIState.Success -> {
                    val qr = (uiState as QrDetailsUIState.Success).qr
                    val qrSize = screenWidth * 0.5f

                    // Generate QR bitmap for preview
                    val qrBitmap = remember(qr.id) {
                        QrHelper.generateQrBitmap(
                            content = qr.shortLink,
                            fgColorHex = qr.fgColor,
                            bgColorHex = qr.bgColor,
                            size = qrSize.value.toInt()
                        ).asImageBitmap()
                    }

                    // Editable fields
                    var title by remember { mutableStateOf(qr.title) }
                    var redirectUrl by remember { mutableStateOf(qr.redirectUrl) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .padding(8.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // QR preview
                                Image(
                                    bitmap = qrBitmap,
                                    contentDescription = "QR Preview",
                                    modifier = Modifier.size(qrSize)
                                )
                                Spacer(Modifier.height(16.dp))

                                // Title input
                                OutlinedTextField(
                                    value = title,
                                    onValueChange = { title = it },
                                    label = { AppText("QR Name") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(Modifier.height(8.dp))

                                // Redirect URL input
                                OutlinedTextField(
                                    value = redirectUrl,
                                    onValueChange = { redirectUrl = it },
                                    label = { AppText("Redirect URL") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(Modifier.height(16.dp))

                                // Save button
                                AppButton(
                                    onClick = { viewModel.updateQr(qr.copy(title = title, redirectUrl = redirectUrl)) },
                                    text = "Save Changes",
                                    containerColor = Color(0xFF5AA0FF),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(Modifier.height(12.dp))

                                // Back button
                                AppButton(
                                    onClick = onBack,
                                    text = "Back",
                                    containerColor = Color(0xFF7A4DCC),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}