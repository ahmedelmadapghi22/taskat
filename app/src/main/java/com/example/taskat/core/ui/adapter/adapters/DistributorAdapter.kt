package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.listener.SetOnClickPersonListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickWhats
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.adapter.viewholder.DistributorViewHolder
import com.example.taskat.databinding.DistributorItemBinding
import com.example.taskat.domain.model.Distributor
import javax.inject.Inject

class DistributorAdapter @Inject constructor(
    private val setOnClickWhats: SetOnClickWhats,
    private val setOnDeleteListener: SetOnDeleteListener,
    private val setOnClickPersonListener: SetOnClickPersonListener
) :
    RecyclerView.Adapter<DistributorViewHolder>() {
    private val listDiffer = AsyncListDiffer(this, diffUtil)
    fun getListDiffer() = listDiffer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistributorViewHolder {

        val binding =
            DistributorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DistributorViewHolder(
            binding,
            setOnClickWhats,
            setOnDeleteListener,
            setOnClickPersonListener
        )


    }

    override fun onBindViewHolder(holder: DistributorViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])


    }


    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Distributor>() {
            override fun areItemsTheSame(oldItem: Distributor, newItem: Distributor): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Distributor, newItem: Distributor): Boolean {


                return oldItem.name == newItem.name && oldItem.phone == newItem.phone
            }

        }
    }


}