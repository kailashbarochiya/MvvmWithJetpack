package com.mvvm.demo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.mvvm.demo.data.repository.AppPreference
import javax.inject.Inject

/**
 * Created by
 */
open class BaseVM : ViewModel() {

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCleared() {
        super.onCleared()
    }

}