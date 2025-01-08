package com.akashsoam.productsapp.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.akashsoam.productsapp.util.workers.SyncProductsWorker
import java.util.concurrent.TimeUnit

class WorkScheduler(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleProductSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncProductsWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "syncProducts",
            ExistingPeriodicWorkPolicy.KEEP, // keep the prev jb tk vo finish nhi hojata
            syncWorkRequest
        )
    }
}
