package com.example.taskat.core.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.databinding.ItemSpecializtionBinding
import com.example.taskat.domain.model.Specialization

class SpecializationViewHolder(
    private val specializationsBinding: ItemSpecializtionBinding,
    private val setOnDeleteListener: SetOnDeleteListener
) : RecyclerView.ViewHolder(specializationsBinding.root), IBind<Specialization> {


    override fun bind(model: Specialization) {
        specializationsBinding.tvNameSpecializations.text = model.name
        specializationsBinding.tvNoSpecializations.text = "${adapterPosition+1}"
        specializationsBinding.btnDeleteSpecialization.setOnClickListener {
            setOnDeleteListener.onClickDelete(model.id)
        }
    }


}