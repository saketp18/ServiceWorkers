package com.unacademy.lite.serviceworkers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unacademy.lite.serviceworkers.utils.fetchImage1Url
import com.unacademy.lite.serviceworkers.utils.fetchImage2Url
import com.unacademy.lite.serviceworkers.workers.ServiceWorker
import okhttp3.OkHttpClient
import okhttp3.Request


class MainViewModel : ViewModel() {

    private val serviceWorker1 = ServiceWorker("ServiceWorker1")
    private val serviceWorker2 = ServiceWorker("ServiceWorker2")


    private val _fetchImage2 = MutableLiveData<Bitmap>()
    val fetchImage2: LiveData<Bitmap>
        get() = _fetchImage2


    fun fetchImage1AndSet() {
        serviceWorker1.addTask(object : ServiceWorker.Task<Bitmap?> {

            @WorkerThread
            override fun onExecuteTask(): Bitmap? {
                println("onExecuteTask ${Thread.currentThread().name}")
                val request = Request.Builder().url(fetchImage1Url).build()
                val response = OkHttpClient().newCall(request).execute()
                return BitmapFactory.decodeStream(response.body()?.byteStream())
            }

            override fun onTaskComplete(result: Bitmap?) {
                _fetchImage1.value = result
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
    }

    private val _fetchImage1 = MutableLiveData<Bitmap>()
    val fetchImage1: LiveData<Bitmap>
        get() = _fetchImage1

    fun fetchImage2AndSet() {
        serviceWorker2.addTask(object : ServiceWorker.Task<Bitmap?> {

            @WorkerThread
            override fun onExecuteTask(): Bitmap? {
                println("onExecuteTask ${Thread.currentThread().name}")
                val request = Request.Builder().url(fetchImage2Url).build()
                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                return BitmapFactory.decodeStream(response.body()?.byteStream())
            }

            override fun onTaskComplete(result: Bitmap?) {
                _fetchImage2.value = result
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        serviceWorker1.shutDown()
        serviceWorker2.shutDown()
    }
}
