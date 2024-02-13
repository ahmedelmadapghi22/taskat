package com.example.taskat.core.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.taskat.databinding.ChooseFilterMethodLayoutBinding

enum class SearchMethod {
    BY_NAME, BY_SPECIALIZATION
}

class ChooseFilterMethodDialog constructor(context: Context) :
    AlertDialog(context) {
    private val layoutMethodLayoutBinding: ChooseFilterMethodLayoutBinding =
        ChooseFilterMethodLayoutBinding.inflate(LayoutInflater.from(context))
    private var searchMethod: SearchMethod = SearchMethod.BY_NAME
    fun getSearchMethod() = searchMethod
    private fun setSearchMethod(searchMethod: SearchMethod) {
        this.searchMethod = searchMethod
    }


    fun showDialog() {
        layoutMethodLayoutBinding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                layoutMethodLayoutBinding.radioByName.id -> {
                    setSearchMethod(SearchMethod.BY_NAME)
                }

                layoutMethodLayoutBinding.radioBySpecialization.id -> {
                    setSearchMethod(SearchMethod.BY_SPECIALIZATION)

                }
            }

        }
        if (searchMethod == SearchMethod.BY_NAME) {
            layoutMethodLayoutBinding.radioByName.isChecked = true
        }
        if (searchMethod == SearchMethod.BY_SPECIALIZATION) {
            layoutMethodLayoutBinding.radioBySpecialization.isChecked = true
        }

        layoutMethodLayoutBinding.btnDismissDialog.setOnClickListener {
            dismiss()
        }
        setView(layoutMethodLayoutBinding.root)
        setCanceledOnTouchOutside(false)
        create()
        show()
    }
}