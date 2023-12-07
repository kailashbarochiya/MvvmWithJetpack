package com.mvvm.demo.domain.model

import com.google.gson.annotations.SerializedName


data class CoinsRateDataResponse(
    @SerializedName("symbol") var symbol: String?=null,
    @SerializedName("openPrice") var openPrice: String?=null)