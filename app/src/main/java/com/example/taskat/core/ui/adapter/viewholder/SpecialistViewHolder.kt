package com.example.taskat.core.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.adapter.listener.SetOnClickEditListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickPersonListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickSpecializationsListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickWhats
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.core.ui.helper.CheckerVisibility
import com.example.taskat.databinding.ItemSpecialistBinding
import com.example.taskat.domain.model.Specialist
import com.example.taskat.domain.util.PreCondition

class SpecialistViewHolder(
    private val itemSpecialistBinding: ItemSpecialistBinding,
    private val setOnClickWhats: SetOnClickWhats,
    private val setOnDeleteListener: SetOnDeleteListener,
    private val setOnClickPersonListener: SetOnClickPersonListener,
    private val setOnClickSpecializationsListener: SetOnClickSpecializationsListener,
    private val setOnClickEditListener: SetOnClickEditListener<Specialist>
) : RecyclerView.ViewHolder(itemSpecialistBinding.root), IBind<Specialist> {
    override fun bind(model: Specialist) {
        itemSpecialistBinding.tvNameSpecialist.text = model.name
        itemSpecialistBinding.tvPhoneSpecialist.text = model.phone
        itemSpecialistBinding.ratingBar.rating = (model.evaluation).toFloat()
        if (!PreCondition.checkEmpty(model.notes)) {
            itemSpecialistBinding.tvNoteSpecialist.text = model.notes
        } else {
            CheckerVisibility.setVisibility(itemSpecialistBinding.tvNoteSpecialist, View.GONE)
        }

        itemSpecialistBinding.btnEditSpecialist.setOnClickListener {
            setOnClickEditListener.onClickEdit(model)
        }
        itemSpecialistBinding.btnGoToSpecializations.setOnClickListener {
            setOnClickSpecializationsListener.onClickSpecializations(model.id)
        }
        itemSpecialistBinding.btnWhatsapp.setOnClickListener {
            setOnClickWhats.talk(model.phone)
        }
        itemSpecialistBinding.btnDeleteSpecialist.setOnClickListener {
            setOnDeleteListener.onClickDelete(model.id)
        }
        itemSpecialistBinding.root.setOnClickListener {
            setOnClickPersonListener.onClickRoot(model.id, model.name)
        }

    }


}