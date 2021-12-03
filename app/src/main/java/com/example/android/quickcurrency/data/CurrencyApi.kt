package com.example.android.quickcurrency.data

import com.example.android.quickcurrency.common.Constants.API_KEY
import com.example.android.quickcurrency.data.models.CurrencyResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


//https://freecurrencyapi.net/api/v2/latest?base_currency=SGD&apikey=f8fbdc40-5398-11ec-b8f1-757e371ba593

interface CurrencyApi {

    @GET("latest")
    suspend fun getCurrency(
        @Query("base_currency") base: String,
        @Query("apikey") apiKey: String = API_KEY
    ) : Response<CurrencyResponse>
}
