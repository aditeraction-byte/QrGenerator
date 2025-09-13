package com.example.qrgenerator.presentation.screens.qrStats

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qrgenerator.domain.model.QrScanDomain
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrStatsScreen(
    qrId: String,
    viewModel: QrStatsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState(initial = QrStatsUIState.Loading)

    LaunchedEffect(qrId) { viewModel.loadScans(qrId) }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QR Statistics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF7A4DCC))
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is QrStatsUIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF7A4DCC))
                    }
                }
                is QrStatsUIState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (uiState as QrStatsUIState.Error).message,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                is QrStatsUIState.Success -> {
                    val scans = (uiState as QrStatsUIState.Success).scans
                    StatsContentEnhanced(scans = scans)
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun StatsContentEnhanced(scans: List<QrScanDomain>) {
    if (scans.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No scans available", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    val totalScans = scans.size
    val latestScan = scans.maxByOrNull { it.timestamp }
    val scansByCountry = scans.groupingBy { it.country ?: "Unknown" }.eachCount()
    val topCountry = scansByCountry.maxByOrNull { it.value }?.key ?: "N/A"
    val uniqueCountries = scansByCountry.keys.size
    val scansByDay = scans.groupingBy { SimpleDateFormat("dd/MM/yyyy").format(Date(it.timestamp)) }.eachCount()
    val avgScansPerDay = (scansByDay.values.sum().toFloat() / scansByDay.size).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        StatCardLarge(title = "Total Scans", value = totalScans.toString(), icon = Icons.Default.Star, modifier = Modifier.weight(1f))
        StatCardLarge(
            title = "Last Scan",
            value = latestScan?.timestamp?.let { SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(it)) } ?: "N/A",
            icon = Icons.Default.DateRange,
            modifier = Modifier.weight(1f)
        )
        StatCardLarge(title = "Top Country", value = topCountry, icon = Icons.Default.LocationOn, modifier = Modifier.weight(1f))
        StatCardLarge(title = "Countries Count", value = uniqueCountries.toString(), icon = Icons.Default.Person, modifier = Modifier.weight(1f))
        StatCardLarge(title = "Avg Scans/Day", value = avgScansPerDay.toString(), icon = Icons.Default.DateRange, modifier = Modifier.weight(1f))
    }
}

@Composable
fun StatCardLarge(title: String, value: String, icon: ImageVector? = null, @SuppressLint("ModifierParameter") modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF7A4DCC),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7A4DCC)
                )
            }
        }
    }
}
