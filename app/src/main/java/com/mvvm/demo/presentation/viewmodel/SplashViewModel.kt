package com.mvvm.demo.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import com.mvvm.demo.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : BaseVM(), LifecycleObserver {


}