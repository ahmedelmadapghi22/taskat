package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.viewholder.CurrencySummaryViewHolder
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.ItemTotalCurrencyMoneyBinding
import com.example.taskat.domain.model.CurrencySummary
import javax.inject.Inject

class CurrencySummaryAdapter @Inject constructor(
    private val stringResourceHelper: StringResourceHelper
) :
    RecyclerView.Adapter<CurrencySummaryViewHolder>() {
    private lateinit var listCurrency: List<CurrencySummary>

    fun setSummaryList(listCurrency: List<CurrencySummary>) {
        this.listCurrency = listCurrency
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencySummaryViewHolder {

        val binding =
            ItemTotalCurrencyMoneyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CurrencySummaryViewHolder(
            binding, stringResourceHelper
        )


    }

    override fun onBindViewHolder(holder: CurrencySummaryViewHolder, position: Int) {
        holder.bind(listCurrency[position])


    }


    override fun getItemCount(): Int {
        return listCurrency.size
    }


}