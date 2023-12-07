package com.mvvm.demo.data.remote

import com.mvvm.demo.domain.model.CoinsRateDataResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by
 */
@JvmSuppressWildcards
interface ApiClient {

    /** Generic [GET] Service
     * which response have status,message and data
     * - data can object,list,list of object,map
     *  - data can be null if there any error
     * */
    @GET("{endpoint}")
    suspend fun getApiCallAsync(
        @Path("endpoint", encoded = true) endPoint: String,
        @QueryMap(encoded = true) params: Map<String, String>
    ): Response<List<CoinsRateDataResponse>>


}
