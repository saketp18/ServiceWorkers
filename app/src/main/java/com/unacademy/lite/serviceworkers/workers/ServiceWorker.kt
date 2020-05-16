package com.unacademy.lite.serviceworkers.workers

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class ServiceWorker(serviceWorkerName: String) {

    private val _workerName = serviceWorkerName
    private val executors = Executors.newSingleThreadExecutor()
    private val singleThread = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
    private val scope = CoroutineScope(SupervisorJob() + singleThread)
    private val handler = Handler(Looper.getMainLooper())

    fun addTask(task: Task<String>) {
        runBlocking {
            scope.launch {
                //println(_workerName)
                task.onExecuteTask()
                handler.post {
                    task.onTaskComplete()
                }
            }
        }
    }

    fun shutDown() {
        scope.cancel()
    }

    interface Task<T> {
        fun onExecuteTask(): T
        fun onTaskComplete()
    }

}
