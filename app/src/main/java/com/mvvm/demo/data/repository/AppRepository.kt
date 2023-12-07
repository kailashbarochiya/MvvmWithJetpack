package com.mvvm.demo.data.repository

import android.util.Log
import com.mvvm.demo.util.AppConstant.ENDPOINT_TICKER
import com.mvvm.demo.data.remote.AppRestApi
import com.mvvm.demo.util.convertToMap
import com.mvvm.demo.data.remote.DataState
import com.mvvm.demo.util.getType
import com.mvvm.demo.domain.model.CoinsRateDataResponse
import com.mvvm.demo.domain.model.MainRequestWithoutParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by
 */

//    @Inject  lateinit var appRestApi: AppRestApi
//    @Inject  lateinit var appDatabase: AppDatabase

//@Inject constructor
class AppRepository(
    private val appRestApi: AppRestApi,
) {

    suspend fun getDataFromApi(): Flow<DataState<List<CoinsRateDataResponse>>> =
        flow {
            emit(DataState.Loading)
            val request = MainRequestWithoutParams()
            val data = appRestApi.getRequest<List<CoinsRateDataResponse>>(
                ENDPOINT_TICKER,
                request.convertToMap(),
                getType<List<CoinsRateDataResponse>>()
            ) { errorMsg, errorCode ->
                emit(DataState.Error(errorMsg, errorCode))
                Log.d("TAG", errorMsg)
            }

            if (data != null) {
                emit(DataState.Success(data))
            }

        }


}