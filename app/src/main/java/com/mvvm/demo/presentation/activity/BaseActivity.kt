package com.mvvm.demo.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.mvvm.demo.util.shareWhileObserved
import dagger.hilt.android.AndroidEntryPoint
import com.mvvm.demo.util.ConnectionState
import com.mvvm.demo.util.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * Created by
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity()  {

    private lateinit var connectionState: Flow<ConnectionState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeConnectivity()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeConnectivity() {
        connectionState = this
            .observeConnectivityAsFlow()
            .shareWhileObserved(this.lifecycleScope)
            .also { flow ->
                flow.asLiveData().observe(this) { state ->
                    when (state) {
                        ConnectionState.Available -> onConnectivityAvailable()
                        ConnectionState.Unavailable -> onConnectivityUnavailable()
                    }
                }
            }
    }
    fun applicationContext(): Context = applicationContext

    abstract fun onConnectivityAvailable()

    abstract fun onConnectivityUnavailable()



}
