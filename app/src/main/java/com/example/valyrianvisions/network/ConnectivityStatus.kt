package com.example.valyrianvisions.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class ConnectivityStatus {
    object Available : ConnectivityStatus()
    object Unavailable : ConnectivityStatus()
}

fun Context.observeConnectivityAsFlow(): Flow<ConnectivityStatus> = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(ConnectivityStatus.Available)
        }

        override fun onLost(network: Network) {
            trySend(ConnectivityStatus.Unavailable)
        }
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Emit the current connectivity status
    val currentStatus = if (connectivityManager.activeNetwork != null) {
        ConnectivityStatus.Available
    } else {
        ConnectivityStatus.Unavailable
    }
    trySend(currentStatus)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}
