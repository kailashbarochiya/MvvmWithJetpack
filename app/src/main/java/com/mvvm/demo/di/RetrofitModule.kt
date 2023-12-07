package com.mvvm.demo.di

import android.content.Context
import com.mvvm.demo.util.AppConstant
import com.mvvm.demo.BuildConfig
import com.mvvm.demo.MyApplication
import com.mvvm.demo.data.remote.ApiClient
import com.mvvm.demo.data.remote.AppRestApi
import com.mvvm.demo.data.repository.AppRemoteRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by
 */

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val READ_TIMEOUT : Long = 30
    private const val WRITE_TIMEOUT : Long = 30
    private  const val CONNECTION_TIMEOUT : Long = 10
    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: MyApplication): Context {
        return application.applicationContext
    }

    private val httpLoggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }


    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            //hear you can add all headers you want by calling 'requestBuilder.addHeader(name ,  value)'
            it.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
     fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }


    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(headerInterceptor: Interceptor,cache: Cache): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }


    @Provides
    @Singleton
    fun provideAppRestApi(apiClient: ApiClient) : AppRestApi {
        return AppRemoteRepository(apiClient)
    }


}