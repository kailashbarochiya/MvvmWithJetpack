package com.mvvm.demo.data.remote

import java.lang.reflect.Type

/**
 * Created by
 */
interface AppRestApi {



    suspend fun <T : Any> getRequest(
        endPoint: String,
        queryParams: Map<String, String>,
        result: Type,
        errorMessage: suspend (errorMsg: String, errorCode: Int) -> Unit
    ): T?

}