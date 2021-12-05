package com.example.android.quickcurrency.presentation

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.android.quickcurrency.R
import com.example.android.quickcurrency.common.CurrencyEvent
import com.example.android.quickcurrency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = binding.convertResult
        val amountField = binding.amountField
        val fromSpin = binding.fromSpinner
        val toSpin = binding.toSpinner

        toSpin.setSelection(1)

        binding.convertButton.setOnClickListener {

            if (!checkInternet(this)) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            }

            if (amountField.text.isEmpty()) { amountField.setText("1") }

            viewModel.beginConversion(
                amountField.text.toString(),
                fromSpin.selectedItem.toString(),
                toSpin.selectedItem.toString(),
            )
        }

        binding.swapButton.setOnClickListener {
            val temp = fromSpin.selectedItemId.toInt()
            fromSpin.setSelection(toSpin.selectedItemId.toInt())
            toSpin.setSelection(temp)
        }

        viewModel.eventStatus.observe(this) { event ->

            when (event) {
                is CurrencyEvent.Success -> {
                    result.setTextColor(Color.BLACK)
                    result.text = event.result
                    binding.conversionRate.text = event.conversionRate
                    binding.conversionRate.isVisible = event.isRateDisplayed
                }
                is CurrencyEvent.Failed -> {

                    result.text = getString(R.string.Currency_event_failed)
                    Log.d("OnCurrencyEventFailed", event.err)
                }
                else -> Unit
            }
        }
    }

    private fun checkInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }

        } else {

            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}