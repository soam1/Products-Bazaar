package com.akashsoam.productsapp

import android.app.Application
import com.akashsoam.productsapp.util.NetworkMonitor

class ProductApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkMonitor(this).startNetworkCallback()
    }
}