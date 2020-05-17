package com.unacademy.lite.serviceworkers.workers

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.annotation.WorkerThread

class ServiceWorker(serviceWorkerName: String) {

    private val _workerName = serviceWorkerName
    private lateinit var worker: HandlerThread
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var workerHandler: Handler

    init {
        init()
    }

    private fun init() {
        worker = HandlerThread(_workerName)
        worker.start()
        workerHandler = Handler(worker.looper)
    }

    fun <T> addTask(task: Task<T>) {
        println(_workerName)
        workerHandler.post {
            val result = task.onExecuteTask()
            handler.post {
                task.onTaskComplete(result)
            }
        }

    }

    fun shutDown() {
        worker.quit()
    }

    interface Task<T> {
        @WorkerThread
        fun onExecuteTask(): T
        fun onTaskComplete(result : T)
    }
}
