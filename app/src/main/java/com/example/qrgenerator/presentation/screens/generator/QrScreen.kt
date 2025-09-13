package com.example.qrgenerator.presentation.screens.generator

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qrgenerator.utils.helpers.QrHelper


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScreen(viewModel: QrViewModel = hiltViewModel(), onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }


    var shortlink by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var redirectUrl by remember { mutableStateOf("") }

    val qrBitmapPreview = remember(shortlink) {
        try {
            QrHelper.generateQrBitmap(
                content = if (shortlink.isNotBlank()) "https://adit-qr.web.app/qr.html?id=$shortlink" else "preview",
                fgColorHex = "#000000",
                bgColorHex = "#FFFFFF",
                size = 200
            ).asImageBitmap()
        } catch (e: Exception) {
            Log.e("QrScreen", "Error creating QR", e)
            null
        }
    }


    LaunchedEffect(uiState) {
        when (uiState) {
            is QrUIState.Success -> snackbarHostState.showSnackbar("QR created!")
            is QrUIState.Error -> snackbarHostState.showSnackbar((uiState as QrUIState.Error).message)
            else -> {}
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, containerColor = Color.Transparent) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF7A4DCC), Color(0xFF5AA0FF))))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
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
                    Text("QR Generator", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
                    Spacer(Modifier.height(20.dp))

                    val qrBitmapToShow = when (uiState) {
                        is QrUIState.Success -> (uiState as QrUIState.Success).bitmap
                        else -> qrBitmapPreview
                    }

                    qrBitmapToShow?.let { Image(bitmap = it, contentDescription = "QR Preview", modifier = Modifier.size(200.dp)) }

                    Spacer(Modifier.height(20.dp))

                    OutlinedTextField(
                        value = shortlink,
                        onValueChange = { shortlink = it },
                        label = { Text("Shortlink") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("QR Name") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = redirectUrl, onValueChange = { redirectUrl = it }, label = { Text("Redirect URL") }, modifier = Modifier.fillMaxWidth())

                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.createQr(shortlink, title, redirectUrl) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5AA0FF), contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Create QR") }

                    Spacer(Modifier.height(12.dp))
                    Button(onClick = onBack, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7A4DCC), contentColor = Color.White), shape = RoundedCornerShape(12.dp)) {
                        Text("Back")
                    }

                    if (uiState is QrUIState.Error) {
                        Spacer(Modifier.height(12.dp))
                        Text(text = (uiState as QrUIState.Error).message, color = Color.Red, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}





