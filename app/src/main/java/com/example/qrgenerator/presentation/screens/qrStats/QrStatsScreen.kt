package com.example.qrgenerator.presentation.screens.qrStats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.qrgenerator.presentation.components.AppStatCard
import com.example.qrgenerator.presentation.components.AppText
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


/**
 * Composable that displays statistics for a given QR code.
 *
 * @param qrId The ID of the QR code to show statistics for.
 * @param viewModel ViewModel handling QR stats logic (default provided by Hilt).
 * @param onBack Callback when the user presses the back button.
 */
@SuppressLint("ConfigurationScreenWidthHeight", "SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrStatsScreen(
    qrId: String,
    viewModel: QrStatsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState(initial = QrStatsUIState.Loading)
    LaunchedEffect(qrId) { viewModel.loadScans(qrId) }

    val violetSoft = Color(0xFF7A4DCC)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppText("QR Statistics", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = violetSoft)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is QrStatsUIState.Loading -> {
                    // Show a loader while data is being fetched
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenWidth * 0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = violetSoft)
                    }
                }
                is QrStatsUIState.Error -> {
                    // Show error message if fetching fails
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenWidth * 0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            text = (uiState as QrStatsUIState.Error).message,
                            color = Color.Red
                        )
                    }
                }
                is QrStatsUIState.Success -> {
                    // Show stats cards once data is loaded
                    val scans = (uiState as QrStatsUIState.Success).scans

                    val stats = listOf(
                        Triple("Total Scans", scans.size.toString(), Icons.Default.Star),
                        Triple(
                            "Last Scan",
                            scans.maxByOrNull { it.timestamp }?.timestamp?.let {
                                SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(it))
                            } ?: "N/A",
                            Icons.Default.DateRange
                        ),
                        Triple(
                            "Top Country",
                            scans.groupingBy { it.country ?: "Unknown" }.eachCount().maxByOrNull { it.value }?.key ?: "N/A",
                            Icons.Default.Person
                        ),
                        Triple(
                            "Top State",
                            scans.groupingBy { it.state ?: "Unknown" }.eachCount().maxByOrNull { it.value }?.key ?: "N/A",
                            Icons.Default.Place
                        ),
                        Triple(
                            "Top City",
                            scans.groupingBy { it.city ?: "Unknown" }.eachCount().maxByOrNull { it.value }?.key ?: "N/A",
                            Icons.Default.LocationOn
                        ),
                        Triple(
                            "Countries Count",
                            scans.groupingBy { it.country ?: "Unknown" }.eachCount().keys.size.toString(),
                            Icons.Default.Email
                        ),
                        Triple(
                            "States Count",
                            scans.groupingBy { it.state ?: "Unknown" }.eachCount().keys.size.toString(),
                            Icons.Default.Place
                        ),
                        Triple(
                            "Cities Count",
                            scans.groupingBy { it.city ?: "Unknown" }.eachCount().keys.size.toString(),
                            Icons.Default.LocationOn
                        ),
                        Triple(
                            "Avg Scans/Day",
                            (scans.groupingBy { SimpleDateFormat("dd/MM/yyyy").format(Date(it.timestamp)) }
                                .eachCount().values.sum().toFloat() /
                                    scans.groupingBy { SimpleDateFormat("dd/MM/yyyy").format(Date(it.timestamp)) }
                                        .eachCount().size).roundToInt().toString(),
                            Icons.Default.DateRange
                        )
                    )

                    stats.forEach { (title, value, icon) ->
                        AppStatCard(
                            title = title,
                            value = value,
                            icon = icon,
                            iconTint = violetSoft,
                            textColor = violetSoft,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(screenWidth * 0.25f)
                        )
                    }
                }
            }
        }
    }
}


