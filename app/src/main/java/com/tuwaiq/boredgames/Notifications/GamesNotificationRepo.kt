package com.tuwaiq.boredgames.Notifications

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.tuwaiq.boredgames.MainActivity
import java.util.concurrent.TimeUnit

class GamesNotificationRepo() {
    private val list = listOf("Feeling Bored? Fire up the app and play!!", "We missed you").random()

    fun myNotification(mainActivity: MainActivity){
        val myWorkRequest = PeriodicWorkRequest.Builder(GameWorker::class.java, 48, TimeUnit.HOURS)
            .setInputData(workDataOf("title" to "Bored Games", "message" to list))
            .build()
        WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
            "periodicStockWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            myWorkRequest
        )
    }
}