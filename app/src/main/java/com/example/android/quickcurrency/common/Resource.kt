package com.example.android.quickcurrency.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

sealed class CurrencyEvent {
    class Success(val result: String, val conversionRate: String, val isRateDisplayed: Boolean) : CurrencyEvent()
    class Failed(val err: String) : CurrencyEvent()
    object Empty : CurrencyEvent()
}