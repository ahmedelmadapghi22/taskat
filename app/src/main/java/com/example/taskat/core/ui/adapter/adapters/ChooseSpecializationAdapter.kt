package com.example.taskat.core.ui.adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.listener.ChooseSpecializationListener
import com.example.taskat.core.ui.adapter.viewholder.ChooseSpecializationViewHolder
import com.example.taskat.databinding.ItemChooseSpecializationBinding
import com.example.taskat.domain.model.Specialization

class ChooseSpecializationAdapter constructor(private val chooseSpecializationListener: ChooseSpecializationListener) :
    RecyclerView.Adapter<ChooseSpecializationViewHolder>() {
    private var specializations: List<Specialization> = emptyList()
    private var specializationCheckedID: List<Int> = emptyList()

    fun injectSpecializations(specializations: List<Specialization>) {
        this.specializations = specializations
    }

    fun injectSpecializationCheckedID(specializationCheckedID: List<Int>) {
        this.specializationCheckedID = specializationCheckedID
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ChooseSpecializationViewHolder {
        val binding = ItemChooseSpecializationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChooseSpecializationViewHolder(
            binding, chooseSpecializationListener, specializationCheckedID
        )
    }

    override fun getItemCount(): Int {
        return specializations.size
    }

    override fun onBindViewHolder(holder: ChooseSpecializationViewHolder, position: Int) {
        holder.bind(specializations[position])
    }
}