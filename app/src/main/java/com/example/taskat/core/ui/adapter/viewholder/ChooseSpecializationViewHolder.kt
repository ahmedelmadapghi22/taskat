package com.example.taskat.core.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.adapter.listener.ChooseSpecializationListener
import com.example.taskat.databinding.ItemChooseSpecializationBinding
import com.example.taskat.domain.model.Specialization

class ChooseSpecializationViewHolder(
    private val itemChooseSpecializationBinding: ItemChooseSpecializationBinding,
    private val chooseSpecializationListener: ChooseSpecializationListener,
    private val choosingSpecializations: List<Int>
) : RecyclerView.ViewHolder(itemChooseSpecializationBinding.root), IBind<Specialization> {


    override fun bind(model: Specialization) {
        itemChooseSpecializationBinding.checkbox.text = model.name
        if (choosingSpecializations.isNotEmpty()) {
            if (checkID(model.id)) {
                itemChooseSpecializationBinding.checkbox.isChecked = true
            }
        }
        itemChooseSpecializationBinding.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                chooseSpecializationListener.onChecked(model.id)
            } else {
                chooseSpecializationListener.onUnChecked(model.id)

            }
        }
    }

    private fun checkID(specializationID: Int): Boolean = choosingSpecializations.contains(specializationID)


}