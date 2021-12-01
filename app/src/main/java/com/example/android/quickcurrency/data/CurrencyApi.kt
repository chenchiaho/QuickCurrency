package com.example.android.quickcurrency.data

import com.example.android.quickcurrency.common.Constants.API_KEY
import com.example.android.quickcurrency.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest")
    suspend fun getCurrency(
        @Query("base") base: String,
        @Query("access_key") apiKey: String = API_KEY
    ) : Response<CurrencyResponse>
}