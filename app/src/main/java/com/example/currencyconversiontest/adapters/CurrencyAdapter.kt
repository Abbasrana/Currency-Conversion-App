package com.example.currencyconversiontest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconversiontest.databinding.CurrencyItemViewBinding
import com.example.currencyconversiontest.model.ConversionModel
import com.example.currencyconversiontest.model.CurrencyModel
import com.example.currencyconversiontest.model.CurrencyRatesModel
import com.example.currencyconversiontest.utils.round
import kotlin.math.roundToLong

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private val currencyItems: ArrayList<ConversionModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = CurrencyItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }


    fun updateItems(currencyList: List<ConversionModel>) {
        currencyItems.clear()
        currencyItems.addAll(currencyList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = currencyItems.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currencyItems[position], position)
    }

    inner class CurrencyViewHolder(private val itemBinding: CurrencyItemViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(conversion: ConversionModel, position: Int) {
            itemBinding.apply {
                currencyCode.text = conversion.currency
                conversionRate.text = conversion.amount?.round(conversion.amount).toString()
            }
        }
    }
}