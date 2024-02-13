package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.adapter.viewholder.SpecializationViewHolder
import com.example.taskat.databinding.ItemSpecializtionBinding
import com.example.taskat.domain.model.Specialization
import javax.inject.Inject

class SpecializationAdapter @Inject constructor(
    private val setOnDeleteListener: SetOnDeleteListener,
) :
    RecyclerView.Adapter<SpecializationViewHolder>() {
    private val listDiffer = AsyncListDiffer(this, diffUtil)
    fun getListDiffer() = listDiffer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder {

        val binding =
            ItemSpecializtionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecializationViewHolder(
            binding,
            setOnDeleteListener
        )


    }

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])


    }


    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Specialization>() {
            override fun areItemsTheSame(
                oldItem: Specialization,
                newItem: Specialization
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Specialization,
                newItem: Specialization
            ): Boolean {


                return oldItem.name == newItem.name
            }

        }
    }


}