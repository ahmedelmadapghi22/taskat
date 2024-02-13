package com.example.taskat.core.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskat.core.ui.adapter.adapters.CurrencyAdapter
import com.example.taskat.core.ui.adapter.listener.SetOnClickCurrencyListener
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.databinding.DialogCurrencyBinding
import com.example.taskat.domain.model.Currency


class CurrencyDialog constructor(
    context: Context,
    stringResourceHelper: StringResourceHelper,
    setOnClickCurrencyListener: SetOnClickCurrencyListener
) :
    AlertDialog(context) {


    private val dialogCurrencyBinding: DialogCurrencyBinding =
        DialogCurrencyBinding.inflate(LayoutInflater.from(context))
    private val linearLayout: LinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    private var currentAdapter: CurrencyAdapter =
        CurrencyAdapter(stringResourceHelper, setOnClickCurrencyListener)
    private var currenciesList: List<Currency> = emptyList()

    fun setCurrenciesList(currenciesList: List<Currency>) {
        this.currenciesList = currenciesList
    }

    private fun getCurrenciesList() = currenciesList
     fun setWhereCurrency(whereCurrency:Int) = currentAdapter.setWhereCurrency(whereCurrency)

    fun createDialog() {
        Log.d("getCurrenciesList", ":${getCurrenciesList()}")
        currentAdapter.setListCurrency(getCurrenciesList())
        dialogCurrencyBinding.rvCurrency.adapter = currentAdapter
        dialogCurrencyBinding.rvCurrency.layoutManager = linearLayout
        dialogCurrencyBinding.btnCloseDialog.setOnClickListener {
            dismiss()
        }

    }


    fun showDialog() {
        setView(dialogCurrencyBinding.root)
        setCanceledOnTouchOutside(false)

        create()
        show()
    }
}