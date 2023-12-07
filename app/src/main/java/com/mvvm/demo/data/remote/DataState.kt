package com.mvvm.demo.data.remote


/**
 * Created by
 */


sealed class DataState<out T>(
    val data: T? = null,
) {
    class Success<out T>(data: T) : DataState<T>(data)
    class Error(val exception: String, val errorCode: Int) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}