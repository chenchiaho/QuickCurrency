package com.example.android.quickcurrency.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

        binding.convertButton.setOnClickListener {
            viewModel.beginConversion(
                binding.amountField.text.toString(),
                binding.fromSpinner.selectedItem.toString(),
                binding.toSpinner.selectedItem.toString()
            )
        }

        viewModel.convertStatus.observe(this, Observer { event ->

            when(event) {
                is CurrencyViewModel.CurrencyEvent.Success -> {
                    binding.convertResult.setTextColor(Color.BLACK)
                    binding.convertResult.text = event.result
                }
                is CurrencyViewModel.CurrencyEvent.Failed -> {
                    binding.convertResult.setTextColor(Color.RED)
                    binding.convertResult.text = event.err
                }
                else -> Unit
            }
        })

    }
}