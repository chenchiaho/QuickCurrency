package com.example.android.quickcurrency.di

import com.example.android.quickcurrency.common.Constants.BASE_URL
import com.example.android.quickcurrency.data.CurrencyApi
import com.example.android.quickcurrency.data.repositories.CurrencyRepository
import com.example.android.quickcurrency.data.repositories.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository =
        CurrencyRepositoryImpl(api)

}