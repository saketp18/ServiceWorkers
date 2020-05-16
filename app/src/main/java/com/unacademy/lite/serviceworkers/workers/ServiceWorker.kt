package com.unacademy.lite.serviceworkers.workers

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class ServiceWorker(serviceWorkerName: String) {

    private val _workerName = serviceWorkerName
    private val executors = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun addTask(task: Task<String>) {
        executors.execute {
            //println(_workerName)
            task.onExecuteTask()
            handler.post {
                task.onTaskComplete()
            }
        }
    }

    fun shutDown() {
        executors.shutdown()
    }

    interface Task<T> {
        fun onExecuteTask(): T
        fun onTaskComplete()
    }

}
