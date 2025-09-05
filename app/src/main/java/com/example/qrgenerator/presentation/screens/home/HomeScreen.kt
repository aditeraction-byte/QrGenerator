package com.example.qrgenerator.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCreateQr: () -> Unit,
    onQrDetail: (String) -> Unit,
    onQrStats: (String) -> Unit,
    reloadTrigger: Boolean
) {
    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(reloadTrigger) {
        viewModel.loadQrs()
    }

    Box(modifier = Modifier.fillMaxSize().background(
        Brush.verticalGradient(listOf(Color(0xFF6A11CB), Color(0xFF2575FC)))
    )) {
        when(uiState) {
            is HomeUIState.Loading -> CircularProgressIndicator(color = Color.White, modifier = Modifier.align(Alignment.Center))
            is HomeUIState.Error -> Text((uiState as HomeUIState.Error).message, color = Color.Red, modifier = Modifier.align(Alignment.Center))
            is HomeUIState.Success -> {
                val list = (uiState as HomeUIState.Success).qrList
                LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Button(onClick = onCreateQr, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2575FC), contentColor = Color.White)) {
                            Text("New QR")
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    items(list) { qr ->
                        Card(modifier = Modifier.fillMaxWidth().clickable { onQrDetail(qr.id) }, shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(qr.title.ifEmpty { "QR ${qr.id}" }, style = MaterialTheme.typography.titleMedium)
                                Text(qr.shortLink, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Text("Redirect: ${qr.redirectUrl}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Spacer(Modifier.height(4.dp))
                                Button(onClick = { onQrStats(qr.id) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2575FC), contentColor = Color.White), shape = RoundedCornerShape(12.dp)) {
                                    Text("See Stats")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}