package com.example.taskat.core.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.adapter.listener.SetOnClickCountry
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.ItemCountryBinding
import com.example.taskat.domain.model.Country
import javax.inject.Inject

class CountryViewHolder(
    private val countryBinding: ItemCountryBinding,
    private val setOnClickCountry: SetOnClickCountry,
) : RecyclerView.ViewHolder(countryBinding.root), IBind<Country> {
    @Inject
    lateinit var stringResourceHelper: StringResourceHelper

    override fun bind(model: Country) {
        countryBinding.tvCountryName.text = stringResourceHelper.getStringFromRes(model.nameResId)
        countryBinding.tvCountryCode.text = model.countryCode
        countryBinding.root.setOnClickListener {
            setOnClickCountry.onClick(model.countryCode)
        }
    }


}