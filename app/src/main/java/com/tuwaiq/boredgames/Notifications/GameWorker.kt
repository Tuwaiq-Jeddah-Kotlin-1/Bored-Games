package com.tuwaiq.boredgames.Notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class GameWorker(private val context: Context, private val params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result{
        NotificationPointer(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())
        return Result.success()

    }
}