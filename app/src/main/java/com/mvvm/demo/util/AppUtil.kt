package com.mvvm.demo.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import java.io.IOException
import java.io.Reader
import java.lang.reflect.Type

/**
 * Created by
 */


fun <T> T.convertToMap(): Map<String, String> = with(Gson().toJson(this)) {
    Gson().fromJson<Map<String, String>>(
        this,
        object : TypeToken<Map<String, String>>() {}.type
    ) ?: emptyMap()
}

inline fun <reified T> getType(): Type = object : TypeToken<T>() {}.type

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)
inline fun <reified T> Reader.fromJson(): T = Gson().fromJson(this, T::class.java)
inline fun <reified T> T.toJsonString(): String = Gson().toJson(this, T::class.java)

class NetworkException(exception: String) : IOException(exception)

fun <T> Flow<T>.shareWhileObserved(coroutineScope: CoroutineScope) = shareIn(
    scope = coroutineScope,
    started = SharingStarted.WhileSubscribed(),
    replay = 0
)