package com.mvvm.demo.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.mvvm.demo.databinding.ActivitySplashBinding
import com.mvvm.demo.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by
 */

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var activitySplashBinding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels<SplashViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)
        lifecycle.addObserver(splashViewModel)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)

            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            finish()
        }


    }

    override fun onStart() {
        super.onStart()
    }


    override fun onConnectivityAvailable() {
        Log.d("TAG", "onConnectivityAvailable")
    }

    override fun onConnectivityUnavailable() {
        Log.d("TAG", "onConnectivityUnavailable")
    }


}