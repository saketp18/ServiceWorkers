package com.unacademy.lite.serviceworkers.workers

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.WorkerThread
import java.util.concurrent.ThreadPoolExecutor

class ServiceWorker(serviceWorkerName: String) {

    private val _workerName = serviceWorkerName
    private lateinit var worker: Worker
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var workerHandler: Handler

    init {
        init()
    }

    private fun init() {
        worker = Worker()
        worker.start()
        workerHandler = Handler(worker.looper)
    }

    fun addTask(task: Task<String>) {
        println(_workerName)
        workerHandler.post {
            task.onExecuteTask()
            handler.post {
                task.onTaskComplete()
            }
        }

    }

    fun shutDown() {
        worker.quit()
    }

    interface Task<T> {
        @WorkerThread
        fun onExecuteTask(): T

        fun onTaskComplete()
    }

}
