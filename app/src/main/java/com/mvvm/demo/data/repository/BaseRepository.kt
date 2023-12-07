package com.mvvm.demo.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mvvm.demo.util.NetworkException
import com.mvvm.demo.util.fromJson
import com.mvvm.demo.util.getType
import com.mvvm.demo.util.toJsonString
import com.mvvm.demo.domain.model.CoinsRateDataResponse
import retrofit2.Response
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Created by
 */
open class BaseRepository {
    private val response = getType<List<CoinsRateDataResponse>>()

    protected suspend fun <T : Any> doNetworkCall(
        call: suspend () -> Response<List<CoinsRateDataResponse>>,
        resultType: Type,
        apiName: String,
        errorFun: suspend (errorMessage: String, errorCode: Int) -> Unit,
    ): T? {
        return try {
            val response = call.invoke()
            when (response.code()) {
                in 200..299 -> { // success
                    response.body()?.let {
                        when (resultType) {
                            this.response -> {
                                // return data field
                                Gson().fromJson<T>(it.toJsonString(), resultType)
                            }

                            else -> {
                                Gson().fromJson<T>(it.toJsonString(), resultType)
                            }
                        }
                    }
                }

                in 400 until 500 -> { // client errors
                    val error = response.errorBody()?.string()?.fromJson<CoinsRateDataResponse>()
                    Log.e(
                        "",
                        "Client Error(${response.code()}): ${response.message()} / ${
                            response.errorBody()
                                ?.string()
                        }"
                    )
                    errorFun(
                        "Something went wrong. (Client Error)",
                        response.code()
                    )
                    null
                    //if(apiName == ACCESS_TOKEN_REFRESH_API) null else error as? T
                }

                in 500..600 -> { // server errors
                    Log.e(
                        "",
                        "Server Error(${response.code()}): ${response.message()} / ${response.errorBody()}"
                    )
                    null
                }

                else -> { // other errors
                    Log.e(
                        "",
                        "Other Error(${response.code()}):${response.message()} / ${response.errorBody()}"
                    )
                    errorFun("Something went wrong.", response.code())
                    null
                }
            }
        } catch (ex: Exception) {
            Log.e("", "Exceptions($apiName:${ex.javaClass}): ${ex.localizedMessage}")
            when (ex) {
                is CancellationException -> null // avoid error message for coroutine job cancellation
                is NetworkException -> {
                    errorFun(ex.message ?: "No Internet", -1)
                    null
                }

                is SocketTimeoutException -> {
                    errorFun("Time out.", -2)
                    null
                }

                else -> {
                    errorFun("Something went Wrong", -3)
                    null
                }
            }
        } finally {
            // dismissProgress()
        }
    }

}