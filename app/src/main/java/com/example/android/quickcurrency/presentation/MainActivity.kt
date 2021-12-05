package com.example.android.quickcurrency.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.android.quickcurrency.common.CurrencyEvent
import com.example.android.quickcurrency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Array.set

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

        viewModel.eventStatus.observe(this, Observer { event ->

            when(event) {
                is CurrencyEvent.Success -> {
                    result.setTextColor(Color.BLACK)
                    result.text = event.result
                    binding.conversionRate.text = event.conversionRate
                    binding.conversionRate.isVisible = event.isRateDisplayed
                }
                is CurrencyEvent.Failed -> {
                    result.setTextColor(Color.RED)
                    result.text = event.err
                }
                else -> Unit
            }
        })
    }
}