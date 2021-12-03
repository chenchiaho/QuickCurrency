package com.example.android.quickcurrency.di

import com.example.android.quickcurrency.common.Constants.BASE_URL
import com.example.android.quickcurrency.data.CurrencyApi
import com.example.android.quickcurrency.data.repositories.CurrencyRepository
import com.example.android.quickcurrency.data.repositories.CurrencyRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): CurrencyApi = Retrofit.Builder()
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()
        .create(CurrencyApi::class.java)


    @Singleton
    @Provides
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository =
        CurrencyRepositoryImpl(api)

}