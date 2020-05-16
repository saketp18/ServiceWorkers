package com.unacademy.lite.serviceworkers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.unacademy.lite.serviceworkers.workers.ServiceWorker

class MainActivity : AppCompatActivity() {

    lateinit var serviceWorker: ServiceWorker
    lateinit var serviceWorker1: ServiceWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serviceWorker = ServiceWorker("ServiceWorkerName")
        serviceWorker1 = ServiceWorker("ServiceWorkerName1")

        //Service 1
        serviceWorker.addTask(object: ServiceWorker.Task<String> {
            override fun onExecuteTask(): String {
                println("onExecuteTask ${Thread.currentThread().name}")
                return "null"
            }

            override fun onTaskComplete() {
                Toast.makeText(this@MainActivity, "serviceworker + task1", Toast.LENGTH_SHORT).show()
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
        serviceWorker.addTask(object: ServiceWorker.Task<String> {
            override fun onExecuteTask(): String {
                println("onExecuteTask ${Thread.currentThread().name}")
                return "null"
            }

            override fun onTaskComplete() {
                Toast.makeText(this@MainActivity, "serviceworker + task2", Toast.LENGTH_SHORT).show()
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
        //Service 2
        serviceWorker1.addTask(object: ServiceWorker.Task<String> {
            override fun onExecuteTask(): String {
                Thread.sleep(6000)
                println("onExecuteTask ${Thread.currentThread().name}")
                return "null"
            }

            override fun onTaskComplete() {
                Toast.makeText(this@MainActivity, "serviceworker1 + task1", Toast.LENGTH_SHORT).show()
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
        serviceWorker1.addTask(object: ServiceWorker.Task<String> {
            override fun onExecuteTask(): String {
                Thread.sleep(8000)
                println("onExecuteTask ${Thread.currentThread().name}")
                return "null"
            }

            override fun onTaskComplete() {
                Toast.makeText(this@MainActivity, "serviceworker1 + task2", Toast.LENGTH_SHORT).show()
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceWorker.shutDown()
        serviceWorker1.shutDown()
    }
}
