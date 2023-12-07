package com.mvvm.demo.presentation.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.demo.data.remote.DataState
import com.mvvm.demo.data.repository.AppRepository
import com.mvvm.demo.domain.model.CoinsRateDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : BaseVM(), LifecycleObserver {

    private val _datastore: MutableLiveData<DataState<List<CoinsRateDataResponse>>> =MutableLiveData()
    val liveData: LiveData<DataState<List<CoinsRateDataResponse>>> = _datastore


    fun fetchData() {

        viewModelScope.launch {
            appRepository.getDataFromApi()
                .flowOn(Dispatchers.IO)
                .collect {
                    when (it) {
                        is DataState.Error ->
                            _datastore.postValue(DataState.Error(it.exception, it.errorCode))
                        is DataState.Loading -> _datastore.value = DataState.Loading
                        is DataState.Success -> {
                            _datastore.postValue(it)
                        }

                    }
                }
        }
    }


}