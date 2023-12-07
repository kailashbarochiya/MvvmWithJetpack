package com.mvvm.demo.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import com.mvvm.demo.R
import com.mvvm.demo.databinding.ActivityHomeBinding
import com.mvvm.demo.presentation.fragment.HomeFragment

import com.mvvm.demo.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by
 */

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        lifecycle.addObserver(homeViewModel)

        setSupportActionBar(activityHomeBinding.toolbar)


        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_home_flHome, fragment)
        transaction.commit()


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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


}