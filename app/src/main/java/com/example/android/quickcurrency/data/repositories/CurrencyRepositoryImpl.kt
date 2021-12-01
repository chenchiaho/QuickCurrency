package com.example.android.quickcurrency.data.repositories

import com.example.android.quickcurrency.common.Resource
import com.example.android.quickcurrency.data.CurrencyApi
import com.example.android.quickcurrency.data.models.CurrencyResponse
import java.lang.Exception
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
    ) : CurrencyRepository {

    override suspend fun getCurrency(base: String, apiKey: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getCurrency(base, apiKey)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(null, response.message())
            }

        } catch (e: Exception) {
            Resource.Error(null, e.message ?: "An error occurred")
        }
    }
}