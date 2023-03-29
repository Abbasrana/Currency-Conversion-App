package com.example.currencyconversiontest.ui.conversion

import android.R
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currencyconversiontest.adapters.CurrencyAdapter
import com.example.currencyconversiontest.databinding.ConversionFragmentBinding
import com.example.currencyconversiontest.model.currenciesToString
import com.example.currencyconversiontest.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversionFragment : Fragment() {
    private var _binding: ConversionFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ConversionViewModel by viewModels()
    lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var currencyAdapter: CurrencyAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ConversionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        initObservations()
    }

    private fun setupViews() {
        context?.let { ctx ->
            //Currency Drop down
            arrayAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            with(binding.currencySpinner)
            {
                adapter = arrayAdapter
                setSelection(0, false)
                gravity = Gravity.LEFT

            }
            binding.currencySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val amount = binding.amount.text
                        if(amount.isNotEmpty()){
                            viewModel.currencyConversion(
                                amount.toString().toDouble(),
                                parent?.getItemAtPosition(position) as String
                            )
                        }
                    }

                }
            val layoutManager = GridLayoutManager(ctx, 3)
            binding.conversionRV.layoutManager = layoutManager
            currencyAdapter = CurrencyAdapter()
            binding.conversionRV.adapter = currencyAdapter
        }
    }

    private fun initObservations() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    binding.progressCurrency.visibility = View.VISIBLE
                    binding.conversionRV.visibility = View.GONE
                }
                is ContentState -> {
                    binding.progressCurrency.visibility = View.GONE
                    binding.currencySpinner.visibility = View.VISIBLE
                    binding.conversionRV.visibility = View.VISIBLE
                }
                is EmptyState -> {
                    binding.progressCurrency.visibility = View.GONE
                    binding.conversionRV.visibility = View.GONE
                }
                is ErrorState -> {
                    //   binding.progressPhotos.gone()
                    binding.progressCurrency.visibility = View.GONE
                    binding.conversionRV.visibility = View.GONE
                    showToast(state.message)
                }

            }
        }
        viewModel.currencyListLiveData.observe(viewLifecycleOwner) { currencyList ->
            arrayAdapter.clear()
            arrayAdapter.addAll(currenciesToString(currencyList))

        }

        viewModel.exchangedCurrencyListListLiveData.observe(viewLifecycleOwner) {
            currencyAdapter.updateItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}