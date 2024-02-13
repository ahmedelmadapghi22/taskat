package com.example.taskat.core.ui.adapter.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.ItemCurrencyBinding
import com.example.taskat.domain.model.Currency
import javax.inject.Inject

class CurrencyViewHolder @Inject constructor(
    private val itemCurrencyBinding: ItemCurrencyBinding,
    private val stringResourceHelper: StringResourceHelper

) : RecyclerView.ViewHolder(itemCurrencyBinding.root), IBind<Currency> {

    override fun bind(model: Currency) {
        itemCurrencyBinding.textView.text =
            stringResourceHelper.getStringFromRes(model.resCurrencyID)
        itemCurrencyBinding.ivCountry.setImageResource(model.imageCountryID)

    }






}