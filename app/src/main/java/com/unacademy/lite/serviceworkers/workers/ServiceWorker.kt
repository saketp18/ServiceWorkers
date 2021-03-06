package com.unacademy.lite.serviceworkers.workers

import android.os.Handler
import android.os.Looper
import androidx.annotation.WorkerThread
import java.util.concurrent.Executors

class ServiceWorker(serviceWorkerName: String) {

    private val _workerName = serviceWorkerName
    private val executors = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun <T> addTask(task: Task<T>) {
        executors.execute {
            println(_workerName)
            val result = task.onExecuteTask()
            handler.post {
                task.onTaskComplete(result)
            }
        }
    }

    fun shutDown() {
        executors.shutdown()
    }

    interface Task<T> {
        @WorkerThread
        fun onExecuteTask(): T

        fun onTaskComplete(result: T)
    }

}
