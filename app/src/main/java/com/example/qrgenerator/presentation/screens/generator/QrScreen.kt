package com.example.qrgenerator.presentation.screens.generator

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.qrgenerator.presentation.components.AppButton
import com.example.qrgenerator.presentation.components.AppCard
import com.example.qrgenerator.presentation.components.AppText
import com.example.qrgenerator.presentation.components.AppTextField
import com.example.qrgenerator.utils.helpers.QrHelper


@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScreen(
    viewModel: QrViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var shortlink by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var redirectUrl by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            is QrUIState.Success -> snackbarHostState.showSnackbar("QR created!")
            is QrUIState.Error -> snackbarHostState.showSnackbar((uiState as QrUIState.Error).message)
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF7A4DCC), Color(0xFF5AA0FF))))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppText(
                            text = "QR Generator",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Black
                        )

                        Spacer(Modifier.height(16.dp))

                        val qrSize = LocalConfiguration.current.screenWidthDp.dp * 0.5f

                        val qrBitmapPreview = remember(shortlink) {
                            try {
                                QrHelper.generateQrBitmap(
                                    content = if (shortlink.isNotBlank()) "https://adit-qr.web.app/qr.html?id=$shortlink" else "preview",
                                    fgColorHex = "#000000",
                                    bgColorHex = "#FFFFFF",
                                    size = qrSize.value.toInt()
                                ).asImageBitmap()
                            } catch (e: Exception) {
                                Log.e("QrScreen", "Error creating QR", e)
                                null
                            }
                        }

                        val qrBitmapToShow = when (uiState) {
                            is QrUIState.Success -> (uiState as QrUIState.Success).bitmap
                            else -> qrBitmapPreview
                        }

                        qrBitmapToShow?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "QR Preview",
                                modifier = Modifier
                                    .size(qrSize)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        AppTextField(
                            value = shortlink,
                            onValueChange = { shortlink = it },
                            label = "Shortlink",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        AppTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = "QR Name",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        AppTextField(
                            value = redirectUrl,
                            onValueChange = { redirectUrl = it },
                            label = "Redirect URL",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        AppButton(
                            onClick = { viewModel.createQr(shortlink, title, redirectUrl) },
                            text = "Create QR",
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Color(0xFF5AA0FF)
                        )

                        Spacer(Modifier.height(12.dp))

                        AppButton(
                            onClick = onBack,
                            text = "Back",
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = Color(0xFF7A4DCC)
                        )

                        if (uiState is QrUIState.Error) {
                            Spacer(Modifier.height(12.dp))
                            AppText(
                                text = (uiState as QrUIState.Error).message,
                                color = Color.Red
                            )
                        }

                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}





