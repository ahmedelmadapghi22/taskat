package com.example.taskat.core.ui.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.taskat.core.ui.adapter.helper.IBind
import com.example.taskat.core.ui.adapter.listener.SetOnClickPersonListener
import com.example.taskat.core.ui.adapter.listener.SetOnClickWhats
import com.example.taskat.core.ui.adapter.listener.SetOnDeleteListener
import com.example.taskat.databinding.DistributorItemBinding
import com.example.taskat.domain.model.Distributor

class DistributorViewHolder(
    private val distributorItemBinding: DistributorItemBinding,
    private val setOnClickWhats: SetOnClickWhats,
    private val setOnDeleteListener: SetOnDeleteListener,
    private val setOnClickPersonListener: SetOnClickPersonListener
) : RecyclerView.ViewHolder(distributorItemBinding.root), IBind<Distributor> {
    override fun bind(model: Distributor) {
        distributorItemBinding.tvNameDistributor.text = model.name
        distributorItemBinding.tvPhoneDistributor.text = model.phone
        distributorItemBinding.btnWhatsapp.isVisible = true
        distributorItemBinding.btnDeleteDistributor.isVisible = true
        distributorItemBinding.btnWhatsapp.setOnClickListener {
            setOnClickWhats.talk(model.phone)
        }
        distributorItemBinding.btnDeleteDistributor.setOnClickListener {
            setOnDeleteListener.onClickDelete(model.id)
        }
        distributorItemBinding.root.setOnClickListener {
            setOnClickPersonListener.onClickRoot(model.id, model.name)
        }

    }


}