package com.example.taskat.core.ui.dialog

import android.app.AlertDialog
import android.content.Context
import com.example.taskat.R
import com.example.taskat.core.ui.ConstantsEditDialogs.INCOME_PRICE
import com.example.taskat.core.ui.ConstantsEditDialogs.OUTCOME_PRICE
import com.example.taskat.databinding.TaskPriceDialogLayoutBinding


class TaskPriceEditDialog constructor(
    context: Context,
    private val editTaskListener: EditTaskPrice
) : AlertDialog(context) {

    private val dialogPriceTaskBinding: TaskPriceDialogLayoutBinding =
        TaskPriceDialogLayoutBinding.inflate(layoutInflater)
    private var whatPrice = -1
    private var newIncomePrice = ""
    private var newOutcomePrice = ""

    init {
        setOnDismissListener {
            dialogPriceTaskBinding.tvOldPrice.text = ""
        }
    }

    private fun setWhatPrice(whatPrice: Int) {
        this.whatPrice = whatPrice
        when (whatPrice) {
            INCOME_PRICE -> {
                dialogPriceTaskBinding.tvOldPriceTitle.text =
                    context.getString(R.string.old_income_price)

                dialogPriceTaskBinding.tvNewPriceTitle.text =
                    context.getString(R.string.new_income_price)
            }

            OUTCOME_PRICE -> {
                dialogPriceTaskBinding.tvOldPriceTitle.text =
                    context.getString(R.string.old_outcome_price)

                dialogPriceTaskBinding.tvNewPriceTitle.text =
                    context.getString(R.string.new_outcome_price)


            }
        }
    }

    private fun setOldPrice(oldPrice: Float) {
        dialogPriceTaskBinding.tvOldPrice.text = oldPrice.toString()
    }

    private fun getNewPrice(): String {
        when (whatPrice) {
            INCOME_PRICE -> {
                newIncomePrice = dialogPriceTaskBinding.edNewPrice.text.toString()

                return newIncomePrice
            }

            OUTCOME_PRICE -> {
                newOutcomePrice = dialogPriceTaskBinding.edNewPrice.text.toString()
                return newOutcomePrice
            }
        }
        return ""
    }

    private fun getWhatPrice(taskID: Int) {
        when (whatPrice) {
            INCOME_PRICE -> {
                editTaskListener.onEditTaskIncomePrice(taskID, getNewPrice())

            }

            OUTCOME_PRICE -> {
                editTaskListener.onEditTaskOutcomePrice(taskID, getNewPrice())

            }
        }
    }

    fun createDialog(whatPrice: Int, oldPrice: Float, taskID: Int) {
        setWhatPrice(whatPrice)
        setOldPrice(oldPrice)
        dialogPriceTaskBinding.btnOk.setOnClickListener {
            getWhatPrice(taskID)
        }
        dialogPriceTaskBinding.btnCancel.setOnClickListener {
            dismiss()
        }
        setView(dialogPriceTaskBinding.root)
        create()
        show()
    }


}