package com.example.qrgenerator.presentation.screens.generator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.graphics.toColorInt
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun QrScreen(
    viewModel: QrViewModel = hiltViewModel(),
    qrId: String? = null,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()


    var title by remember { mutableStateOf("") }
    var redirectUrl by remember { mutableStateOf("") }
    var fgColor by remember { mutableStateOf("#000000") }
    var bgColor by remember { mutableStateOf("#FFFFFF") }
    var expires by remember { mutableStateOf(false) }
    var expiresAt by remember { mutableStateOf<Long?>(null) }


    LaunchedEffect(qrId) {
        if (qrId != null) {
            viewModel.loadQr(qrId)
        } else {
            viewModel.setNewQrState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is QrUIState.Loading -> CircularProgressIndicator(color = Color.White)

            is QrUIState.Error -> Text(
                text = (uiState as QrUIState.Error).message,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

            is QrUIState.Success -> {
                val qr = (uiState as QrUIState.Success).qr
                val bmp = (uiState as QrUIState.Success).bitmap

                LaunchedEffect(qr) {
                    title = qr.title
                    redirectUrl = qr.redirectUrl
                    fgColor = qr.fgColor
                    bgColor = qr.bgColor
                    expiresAt = qr.expiresAt
                    expires = qr.expiresAt != null
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "QR Generator",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF2575FC)
                        )

                        Spacer(Modifier.height(16.dp))

                        Image(
                            bitmap = bmp,
                            contentDescription = "QR Code",
                            modifier = Modifier.size(200.dp)
                        )

                        Spacer(Modifier.height(12.dp))

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

                        Spacer(Modifier.height(8.dp))

                        Row {
                            ColorPicker(
                                selectedColor = fgColor,
                                onColorSelected = { fgColor = it },
                                label = "Foreground"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            ColorPicker(
                                selectedColor = bgColor,
                                onColorSelected = { bgColor = it },
                                label = "Background"
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = expires,
                                onCheckedChange = {
                                    expires = it
                                    expiresAt = if (it) System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 else null
                                }
                            )
                            Text("Expires in 7 days")
                        }

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (qrId != null) {
                                    viewModel.updateQr(title, redirectUrl, fgColor, bgColor, expiresAt)
                                } else {
                                    viewModel.createQr(title, redirectUrl, fgColor, bgColor, expiresAt)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2575FC),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (qrId != null) "Update QR" else "Create QR")
                        }

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = onBack,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6A11CB),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Back")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPicker(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
    label: String
) {
    val colors = listOf("#000000", "#FFFFFF", "#FF0000", "#00FF00", "#0000FF", "#FFFF00")
    Column {
        Text(label, color = Color.White)
        Row(modifier = Modifier.padding(top = 4.dp)) {
            colors.forEach { colorHex ->
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(colorHex.toColorInt()))
                        .border(
                            width = if (colorHex == selectedColor) 2.dp else 0.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { onColorSelected(colorHex) }
                        .padding(2.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

