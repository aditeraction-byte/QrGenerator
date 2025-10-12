package com.example.qrgenerator.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
/**
 * Scaffold wrapper providing top bar, FAB, and snackbar host.
 *
 * @param topBar Composable for the top bar.
 * @param floatingActionButton Composable for the FAB.
 * @param snackbarHostState Snackbar host state for showing snackbars.
 * @param content Composable content of the screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    topBar: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { topBar?.invoke() },
        floatingActionButton = { floatingActionButton?.invoke() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = content
    )
}