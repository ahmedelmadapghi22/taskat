package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.listener.task.TaskOperations
import com.example.taskat.core.ui.adapter.viewholder.TaskViewHolder
import com.example.taskat.core.ui.helper.StringResourceHelper
import com.example.taskat.data.model.TaskItem
import com.example.taskat.databinding.ItemTaskBinding

class TaskAdapter(
    private val stringResourceHelper: StringResourceHelper,
    private val taskOperations: TaskOperations
) :
    RecyclerView.Adapter<TaskViewHolder>() {

    private val listDiffer = AsyncListDiffer(this, diffUtil)
    fun getListDiffer() = listDiffer


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val binding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, stringResourceHelper, taskOperations)


    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getListDiffer().currentList[position])

    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TaskItem>() {
            override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
                return oldItem.taskID == newItem.taskID
            }

            override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {


                return oldItem.taskState == newItem.taskState && oldItem.taskPrice == newItem.taskPrice && oldItem.taskPriceSpecialist == newItem.taskPriceSpecialist
                        && oldItem.currencyOfDistributor == newItem.currencyOfDistributor && oldItem.currencyOfSpecialist == newItem.currencyOfSpecialist
                        && oldItem.isDistributorAccounted == newItem.isDistributorAccounted && oldItem.isSpecialistAccounted == newItem.isSpecialistAccounted
            }

        }
    }


}