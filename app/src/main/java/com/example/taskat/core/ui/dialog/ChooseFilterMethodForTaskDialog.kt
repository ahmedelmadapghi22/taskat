package com.example.taskat.core.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import com.example.taskat.core.ui.dialog.listener.SetOnClickMethodTaskSearch
import com.example.taskat.databinding.ChooseFilterMethodTaskLayoutBinding


class ChooseFilterMethodForTaskDialog constructor(
    context: Context,
    private val setOnClickMethodTaskSearch: SetOnClickMethodTaskSearch
) :
    AlertDialog(context) {
    private val layoutMethodLayoutBinding: ChooseFilterMethodTaskLayoutBinding =
        ChooseFilterMethodTaskLayoutBinding.inflate(LayoutInflater.from(context))


    fun showDialog() {
        layoutMethodLayoutBinding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            Log.d("radioGroup", "showDialog:${radioGroup.findViewById<RadioButton>(i).text}")
            setOnClickMethodTaskSearch.onClick(radioGroup.findViewById<RadioButton>(i).text.toString())
            dismiss()
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