package com.example.android.quickcurrency.data.repositories

import com.example.android.quickcurrency.common.Resource
import com.example.android.quickcurrency.data.models.CurrencyResponse

interface CurrencyRepository {

    suspend fun getCurrency(base: String, apiKey: String): Resource<CurrencyResponse>
}