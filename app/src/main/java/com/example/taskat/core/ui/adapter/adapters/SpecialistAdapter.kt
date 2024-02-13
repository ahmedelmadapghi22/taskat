package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.listener.SetOnClickEditListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickPersonListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickSpecializationsListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickWhats
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.adapter.viewholder.SpecialistViewHolder
import com.example.taskat.databinding.ItemSpecialistBinding
import com.example.taskat.domain.model.Specialist
import javax.inject.Inject

class SpecialistAdapter @Inject constructor(
    private val setOnClickWhats: SetOnClickWhats,
    private val setOnDeleteListener: SetOnDeleteListener,
    private val setOnClickPersonListener: SetOnClickPersonListener,
    private val setOnClickSpecializationsListener: SetOnClickSpecializationsListener,
    private val setOnClickEditListener: SetOnClickEditListener<Specialist>
) :
    RecyclerView.Adapter<SpecialistViewHolder>() {
    private val listDiffer = AsyncListDiffer(this, diffUtil)
    fun getListDiffer() = listDiffer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialistViewHolder {

        val binding =
            ItemSpecialistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialistViewHolder(
            binding,
            setOnClickWhats,
            setOnDeleteListener,
            setOnClickPersonListener,
            setOnClickSpecializationsListener,
            setOnClickEditListener
        )


    }

    override fun onBindViewHolder(holder: SpecialistViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])


    }


    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Specialist>() {
            override fun areItemsTheSame(oldItem: Specialist, newItem: Specialist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Specialist, newItem: Specialist): Boolean {


                return oldItem.name == newItem.name && oldItem.phone == newItem.phone
            }

        }
    }


}