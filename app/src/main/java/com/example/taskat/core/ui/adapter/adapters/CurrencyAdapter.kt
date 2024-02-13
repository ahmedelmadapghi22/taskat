package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.ConstantsEditDialogs
import com.example.taskat.core.ui.adapter.listener.SetOnClickCurrencyListener
import com.example.taskat.core.ui.adapter.viewholder.CurrencyViewHolder
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.ItemCurrencyBinding
import com.example.taskat.domain.model.Currency
import javax.inject.Inject

/**
 * @param whereCurrency i make this variable because i
 * */
class CurrencyAdapter @Inject constructor(
    private val stringResourceHelper: StringResourceHelper,
    private val setOnClickCurrencyListener: SetOnClickCurrencyListener,
    private var whereCurrency: Int = -1
) :
    RecyclerView.Adapter<CurrencyViewHolder>() {
    private lateinit var listCurrency: List<Currency>
    private var lastSelectedId = -1

    fun setListCurrency(listCurrency: List<Currency>) {
        this.listCurrency = listCurrency
    }

    fun setWhereCurrency(whereCurrency: Int) {
        this.whereCurrency = whereCurrency
    }

    private fun setLastSelectedID(lastSelectedId: Int) {

        this.lastSelectedId = lastSelectedId
    }

    private fun getLastSelectedID() = lastSelectedId


    private fun sendCurrencyIDAccordingWhereCurrency(currencyIDRes: Int) {
        when (whereCurrency) {
            ConstantsEditDialogs.INCOME_CURRENCY -> {
                setOnClickCurrencyListener.onClickDialogIncomeCurrency(currencyIDRes)
            }

            ConstantsEditDialogs.OUTCOME_CURRENCY -> {
                setOnClickCurrencyListener.onClickDialogOutcomeCurrency(currencyIDRes)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {

        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(
            binding, stringResourceHelper
        )


    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(listCurrency[position])
        holder.itemView.isSelected = position == getLastSelectedID()

        holder.itemView.setOnClickListener {


            val lastSelected = getLastSelectedID()
            if (lastSelected != position) {
                setLastSelectedID(position)
                notifyItemChanged(lastSelected)
                notifyItemChanged(position)
            }
            sendCurrencyIDAccordingWhereCurrency(listCurrency[position].resCurrencyID)


        }

    }


    override fun getItemCount(): Int {
        return listCurrency.size
    }


}