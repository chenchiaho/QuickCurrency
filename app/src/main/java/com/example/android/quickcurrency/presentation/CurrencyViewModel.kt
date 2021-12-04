package com.example.android.quickcurrency.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.quickcurrency.common.Resource
import com.example.android.quickcurrency.data.models.Data
import com.example.android.quickcurrency.data.repositories.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class CurrencyViewModel
@Inject constructor(
    private val repository: CurrencyRepository)
 : ViewModel() {


    sealed class CurrencyEvent {
        class Success(val result: String, val conversionRate: String, val displayRate: Boolean): CurrencyEvent()
        class Failed(val err: String): CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    private val _convertStatus = MutableLiveData<CurrencyEvent>(CurrencyEvent.Empty)
    val convertStatus: LiveData<CurrencyEvent>
        get() = _convertStatus

    fun beginConversion(input: String, from: String, to: String) {

        val inputAmount = input.toFloatOrNull()
        viewModelScope.launch() {

//            if (inputAmount == 0.0f || input == null) {
//                _convertStatus.value = CurrencyEvent.Success
//            }

            when ( val response = repository.getCurrency(from)) {

                is Resource.Error -> _convertStatus.postValue(CurrencyEvent.Failed(response.message!!))

                is Resource.Success -> {
                    val responseData = response.data!!.data

                    val rate = getRate(to, responseData).toString().toFloat()
                    if (rate == null) {
                            _convertStatus.postValue(CurrencyEvent.Failed("Unable to retrieve rate"))
                    } else {
                        val output = round(inputAmount!! * rate * 100) / 100
                        val roundedRate = round(rate * 100) / 100
                            _convertStatus.postValue(CurrencyEvent.Success(
                                    "$output", "1 $from = $roundedRate $to", true
                            ))
                    }

                }

        }

        }
    }

    private fun getRate(currencyCode: String, rates: Data) = when (currencyCode) {
            "SGD" -> rates.SGD
            "USD" -> rates.USD
            "TWD" -> rates.TWD
            "AED" -> rates.AED
            "AFN" -> rates.AFN
            "ALL" -> rates.ALL
            "AMD" -> rates.AMD
            "AOA" -> rates.AOA
            "ARS" -> rates.ARS
            "AUD" -> rates.AUD
            "AZN" -> rates.AZN
            "BDT" -> rates.BDT
            "BGN" -> rates.BGN
            "BHD" -> rates.BHD
            "BIF" -> rates.BIF
            "BIH" -> rates.BIH
            "BND" -> rates.BND
            "BOB" -> rates.BOB
            "BRL" -> rates.BRL
            "BSD" -> rates.BSD
            "BTC" -> rates.BTC
            "BWP" -> rates.BWP
            "BYR" -> rates.BYR
            "CAD" -> rates.CAD
            "CDF" -> rates.CDF
            "CHF" -> rates.CHF
            "CLP" -> rates.CLP
            "CNY" -> rates.CNY
            "COP" -> rates.COP
            "CRC" -> rates.CRC
            "CUC" -> rates.CUC
            "CVE" -> rates.CVE
            "CZK" -> rates.CZK
            "DJF" -> rates.DJF
            "DKK" -> rates.DKK
            "DOP" -> rates.DOP
            "DZD" -> rates.DZD
            "EGP" -> rates.EGP
            "ERN" -> rates.ERN
            "ETB" -> rates.ETB
            "ETH" -> rates.ETH
            "EUR" -> rates.EUR
            "FJD" -> rates.FJD
            "GBP" -> rates.GBP
            "GEL" -> rates.GEL
            "GHS" -> rates.GHS
            "GMD" -> rates.GMD
            "GNF" -> rates.GNF
            "GTQ" -> rates.GTQ
            "GYD" -> rates.GYD
            "HKD" -> rates.HKD
            "HNL" -> rates.HNL
            "HRV" -> rates.HRV
            "HTG" -> rates.HTG
            "HUF" -> rates.HUF
            "IDR" -> rates.IRR
            "ILS" -> rates.ILS
            "INR" -> rates.INR
            "IQD" -> rates.IQD
            "IRR" -> rates.IRR
            "ISK" -> rates.ISK
            "JMD" -> rates.JMD
            "JOD" -> rates.JOD
            "JPY" -> rates.JPY
            "KES" -> rates.KES
            "KGS" -> rates.KGS
            "KHR" -> rates.KHR
            "KMF" -> rates.KMF
            "KRW" -> rates.KRW
            "KYD" -> rates.KYD
            "KZT" -> rates.KZT
            "LAK" -> rates.LAK
            "LBP" -> rates.LBP
            "LKR" -> rates.LKR
            "LRD" -> rates.LRD
            "LSL" -> rates.LSL
            "LTC" -> rates.LTC
            "LYD" -> rates.LYD
            "MAD" -> rates.MAD
            "MDL" -> rates.MDL
            "MGA" -> rates.MGA
            "MKD" -> rates.MKD
            "MMK" -> rates.MMK
            "MNT" -> rates.MNT
            "MOP" -> rates.MOP
            "MUR" -> rates.MUR
            "MVR" -> rates.MVR
            "MWK" -> rates.MWK
            "MXN" -> rates.MXN
            "MYR" -> rates.MYR
            "MZN" -> rates.MZN
            "NAD" -> rates.NAD
            "NGN" -> rates.NGN
            "NIO" -> rates.NIO
            "NOK" -> rates.NOK
            "NPR" -> rates.NPR
            "NZD" -> rates.NZD
            "OMR" -> rates.OMR
            "PAB" -> rates.PAB
            "PEN" -> rates.PEN
            "PGK" -> rates.PGK
            "PHP" -> rates.PHP
            "PKR" -> rates.PKR
            "PLN" -> rates.PLN
            "PYG" -> rates.PYG
            "QAR" -> rates.QAR
            "RON" -> rates.RON
            "RSD" -> rates.RSD
            "RUB" -> rates.RUB
            "RWF" -> rates.RWF
            "SAR" -> rates.SAR
            "SYP" -> rates.SYP
            "SZL" -> rates.SZL
            "THB" -> rates.THB
            "TJS" -> rates.TJS
            "TMT" -> rates.TMT
            "UAH" -> rates.UAH
            "UGX" -> rates.UGX
            "XRP" -> rates.XRP
            "YER" -> rates.YER
            "ZAR" -> rates.ZAR

        else -> null
    }
}