package com.example.taskat.core.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.ItemTotalCurrencyMoneyBinding
import com.example.taskat.domain.model.CurrencySummary
import javax.inject.Inject

class CurrencySummaryViewHolder @Inject constructor(
    private val itemTotalCurrencyMoneyBinding: ItemTotalCurrencyMoneyBinding,
    private val stringResourceHelper: StringResourceHelper

) : RecyclerView.ViewHolder(itemTotalCurrencyMoneyBinding.root), IBind<CurrencySummary> {

    override fun bind(model: CurrencySummary) {
        model.apply {
            itemTotalCurrencyMoneyBinding.tvCurrencyName.text =
                stringResourceHelper.getStringFromRes(currencyNameID)
            itemTotalCurrencyMoneyBinding.tvCurrencyTotalMoney.text = model.totalMoney.toString()

        }

    }


}