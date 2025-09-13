package com.example.qrgenerator.presentation.screens.qrDetail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qrgenerator.utils.helpers.QrHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrDetailsScreen(
    qrId: String,
    viewModel: QrDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }


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
                is QrDetailsUIState.Loading -> CircularProgressIndicator(color = Color(0xFF5AA0FF))

                is QrDetailsUIState.Error -> Text(
                    text = (uiState as QrDetailsUIState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )

                is QrDetailsUIState.Success -> {
                    val qr = (uiState as QrDetailsUIState.Success).qr

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(16.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Edit QR",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(20.dp))

                            val qrBitmap = remember(qr.id) {
                                QrHelper.generateQrBitmap(
                                    content = qr.shortLink,
                                    fgColorHex = qr.fgColor,
                                    bgColorHex = qr.bgColor,
                                    size = 200
                                ).asImageBitmap()
                            }

                            Image(bitmap = qrBitmap, contentDescription = "QR Preview", modifier = Modifier.size(200.dp))
                            Spacer(Modifier.height(20.dp))

                            var title by remember { mutableStateOf(qr.title) }
                            var redirectUrl by remember { mutableStateOf(qr.redirectUrl) }

                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it },
                                label = { Text("QR Name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(
                                value = redirectUrl,
                                onValueChange = { redirectUrl = it },
                                label = { Text("Redirect URL") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.updateQr(qr.copy(title = title, redirectUrl = redirectUrl)) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AA0FF), contentColor = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) { Text("Save Changes") }

                            Spacer(Modifier.height(12.dp))
                            Button(
                                onClick = onBack,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7A4DCC), contentColor = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) { Text("Back") }
                        }
                    }
                }
            }
        }
    }
}