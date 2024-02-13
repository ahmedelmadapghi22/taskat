package com.example.taskat.core.ui.adapter.viewholder

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.R
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.adapter.listener.task.TaskOperations
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.data.model.TaskItem
import com.example.taskat.databinding.ItemTaskBinding

class TaskViewHolder(
    private val itemTaskBinding: ItemTaskBinding,
    private val stringResourceHelper: StringResourceHelper,
    private val taskOperations: TaskOperations
) : RecyclerView.ViewHolder(itemTaskBinding.root), IBind<TaskItem> {
    private val taskStateList =
        itemTaskBinding.root.context.resources.getStringArray(R.array.taskState)
    private val getTaskStateIdSList =
        itemTaskBinding.root.context.resources.getIntArray(R.array.taskStateIds)

    override fun bind(model: TaskItem) {
        itemTaskBinding.apply {
            model.apply {
                getState(tvTaskState, taskState)
                tvTaskTitle.text = taskName
                tvTaskDate.text = taskDate
                tvTaskIncome.text = taskPrice.toString()
                tvTaskOutcome.text = taskPriceSpecialist.toString()
                setCurrency(tvTaskCurrencyIncome, currencyOfDistributor)
                setCurrency(tvTaskCurrencyOutcome, currencyOfSpecialist)

                tvTaskDistributor.text = delivererName
                if (recipientName == null) {
                    tvTaskSpecialist.text =
                        stringResourceHelper.getStringFromRes(R.string.noSpecialist)

                } else {
                    tvTaskSpecialist.text = recipientName
                }

                if (isDistributorAccounted == 1) {
                    cbIsDistributorAccounted.isChecked = true
                    cbIsDistributorAccounted.isClickable = false

                } else {
                    cbIsDistributorAccounted.setOnCheckedChangeListener { p0, p1 ->
                        if (p1) {
                            taskOperations.onClickIsDistributorAccounted(taskID)
                        }

                    }

                }


                if (isSpecialistAccounted == 1) {
                    cbIsSpecialistAccounted.isChecked = true
                    cbIsSpecialistAccounted.isClickable = false

                } else {
                    cbIsSpecialistAccounted.setOnCheckedChangeListener { p0, p1 ->
                        if (p1) {
                            taskOperations.onClickIsSpecialistAccounted(taskID)
                        }

                    }

                }
                tvTaskState.setOnClickListener {
                    taskOperations.onClickTaskState(taskID, taskState)
                }
                tvTaskSpecialist.setOnClickListener {
                    taskOperations.onClickTaskSpecialist(taskID)
                }
                tvTaskIncome.setOnClickListener {
                    taskOperations.onClickIncomePrice(taskID, taskPrice)
                }
                tvTaskOutcome.setOnClickListener {
                    taskOperations.onClickOutcomePrice(taskID, taskPriceSpecialist)
                }
                tvTaskCurrencyOutcome.setOnClickListener {
                    taskOperations.onClickOutcomeCurrency(taskID)
                }
                tvTaskCurrencyIncome.setOnClickListener {
                    taskOperations.onClickIncomeCurrency(taskID)
                }

            }
        }

    }

    private fun getState(tvState: TextView, stateID: Int) {
        when (stateID) {
            0 -> {
                tvState.background = ContextCompat.getDrawable(
                    tvState.context, R.drawable.in_process_state_background
                )
            }

            1 -> {
                tvState.background = ContextCompat.getDrawable(
                    tvState.context, R.drawable.complete_state_background
                )

            }

        }
        tvState.text = taskStateList[getTaskStateIdSList.indexOf(stateID)]

    }


    private fun setCurrency(tvCurrency: TextView, currencyID: Int) {
        try {


            if (currencyID == -1) {
                tvCurrency.text = stringResourceHelper.getStringFromRes(R.string.not_defined)

            } else {
                val currency = stringResourceHelper.getStringFromRes(currencyID)
                tvCurrency.text = currency
            }

        } catch (ex: Exception) {
            tvCurrency.text = stringResourceHelper.getStringFromRes(R.string.not_defined)

        }
    }

}