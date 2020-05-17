package com.unacademy.lite.serviceworkers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unacademy.lite.serviceworkers.workers.ServiceWorker

class MainActivity : AppCompatActivity() {

    lateinit var serviceWorker: ServiceWorker
    lateinit var serviceWorker1: ServiceWorker
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serviceWorker = ServiceWorker("ServiceWorkerName")
        serviceWorker1 = ServiceWorker("ServiceWorkerName1")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val image = findViewById<ImageView>(R.id.image)
        if(savedInstanceState == null) {
            viewModel.fetch()
        }

        viewModel.resultData.observe(this, Observer {
            it?.let { bitmap ->
                image.setImageBitmap(bitmap)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        serviceWorker.shutDown()
        serviceWorker1.shutDown()
    }
}
