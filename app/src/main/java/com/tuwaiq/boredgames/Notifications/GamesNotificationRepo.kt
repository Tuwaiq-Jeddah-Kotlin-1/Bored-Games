package com.tuwaiq.boredgames.Notifications

import androidx.work.*
import com.tuwaiq.boredgames.MainActivity
import java.util.concurrent.TimeUnit

class GamesNotificationRepo() {
    private val list = listOf("a", "b", "c").random()

    fun myNotification(mainActivity: MainActivity){
        val myWorkRequest = PeriodicWorkRequest.Builder(GameWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(workDataOf("title" to "Bored Games", "message" to list))
            .build()
        WorkManager.getInstance(mainActivity).enqueueUniquePeriodicWork(
            "periodicStockWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            myWorkRequest
        )
    }
}