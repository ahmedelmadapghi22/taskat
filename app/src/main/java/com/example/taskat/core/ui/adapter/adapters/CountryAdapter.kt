package com.example.taskat.core.ui.adapter.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.taskat.R
import com.example.taskat.core.ui.adapter.listener.SetOnClickCountry
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.ItemCountryBinding
import com.example.taskat.domain.model.Country

class CountryAdapter constructor(
    private val context: Context,
    private val countryList: List<Country>,
    private val stringResourceHelper: StringResourceHelper
) : ArrayAdapter<Country>(
    context,
    R.layout.item_country,
    countryList
) {

    private var filteredCountryList: List<Country> = countryList

    private lateinit var setOnClickCountry: SetOnClickCountry
    fun injectLister(setOnClickCountry: SetOnClickCountry) {
        this.setOnClickCountry = setOnClickCountry
    }


    override fun getCount(): Int {
        return filteredCountryList.size
    }

    override fun getItem(position: Int): Country {
        return filteredCountryList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_country, parent, false)
        val countryBinding: ItemCountryBinding = ItemCountryBinding.bind(itemView)
        countryBinding.tvCountryName.text =
            stringResourceHelper.getStringFromRes(filteredCountryList[position].nameResId)
        countryBinding.tvCountryCode.text = filteredCountryList[position].countryCode
        countryBinding.root.setOnClickListener {
            setOnClickCountry.onClick(filteredCountryList[position].countryCode)
        }

        return itemView

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredList = if (constraint.isNullOrEmpty()) {
                    countryList
                } else {
                    filteredCountryList.filter { country ->

                                country.countryCode.contains(constraint)
                    }
                }

                filterResults.values = filteredList
                filterResults.count = filteredList.size
                return filterResults


            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {

                filteredCountryList = results?.values as? List<Country> ?: emptyList()
                notifyDataSetChanged()
            }

        }
    }
}