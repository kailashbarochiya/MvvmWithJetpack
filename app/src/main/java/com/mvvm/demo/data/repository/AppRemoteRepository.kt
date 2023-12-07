package com.mvvm.demo.data.repository

import com.mvvm.demo.data.remote.ApiClient
import com.mvvm.demo.data.remote.AppRestApi
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created by
 */

class AppRemoteRepository @Inject constructor(val apiClient: ApiClient) : BaseRepository(),
    AppRestApi {


    override suspend fun <T : Any> getRequest(
        endPoint: String,
        queryParams: Map<String, String>,
        result: Type,
        errorMessage: suspend (errorMsg: String, errorCode: Int) -> Unit
    ): T? {
        return doNetworkCall(
            call = { apiClient.getApiCallAsync(endPoint = endPoint, params = queryParams) },
            apiName = endPoint,
            resultType = result,
            errorFun = errorMessage
        )
    }


}