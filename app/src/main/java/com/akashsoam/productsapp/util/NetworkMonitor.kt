package com.akashsoam.productsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.akashsoam.productsapp.util.workers.SyncProductsWorker

class NetworkMonitor(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun startNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            // Trigger sync when the network is available
            WorkManager.getInstance(context)
                .enqueue(OneTimeWorkRequest.from(SyncProductsWorker::class.java))
        }
    }

    fun stopNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
