package com.example.valyrianvisions.network

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ConnectivityStatusBanner() {
    val context = LocalContext.current
    val connectivityStatus by context.observeConnectivityAsFlow().collectAsState(
        initial = ConnectivityStatus.Unavailable
    )

    if (connectivityStatus is ConnectivityStatus.Unavailable) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No internet connection",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
