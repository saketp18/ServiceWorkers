package com.unacademy.lite.serviceworkers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unacademy.lite.serviceworkers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.apply {
            fetchImage1.observe(this@MainActivity, Observer {
                it?.let { bitmap ->
                    activityMainBinding.image1.setImageBitmap(bitmap)
                }
            })

            fetchImage2.observe(this@MainActivity, Observer {
                it?.let { bitmap ->
                    activityMainBinding.image2.setImageBitmap(bitmap)
                }
            })
        }
    }

    private fun setupClickListeners() {
        activityMainBinding.apply {
            button1.setOnClickListener {
                viewModel.fetchImage1AndSet()
            }
            button2.setOnClickListener {
                viewModel.fetchImage2AndSet()
            }
        }
    }
}
