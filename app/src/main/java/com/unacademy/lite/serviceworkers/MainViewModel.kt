package com.unacademy.lite.serviceworkers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unacademy.lite.serviceworkers.workers.ServiceWorker
import okhttp3.OkHttpClient
import okhttp3.Request


class MainViewModel : ViewModel() {

    private val _resultData = MutableLiveData<Bitmap>()
    private val serviceWorker = ServiceWorker("")
    val resultData: LiveData<Bitmap>
        get() = _resultData

    fun fetch() {
        serviceWorker.addTask(object : ServiceWorker.Task<Bitmap?> {
            override fun onExecuteTask(): Bitmap? {
                val request = Request.Builder().url("https://raw.githubusercontent.com/saketp18/MovieSearch/master/demo/app_1.jpg").build()
                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                val bmp = BitmapFactory.decodeStream(response.body()?.byteStream())
                return bmp
            }

            override fun onTaskComplete(result: Bitmap?) {
                _resultData.value = result
                Log.d("Saket", result.toString())
                println("onTaskComplete ${Thread.currentThread().name}")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        serviceWorker
    }
}
