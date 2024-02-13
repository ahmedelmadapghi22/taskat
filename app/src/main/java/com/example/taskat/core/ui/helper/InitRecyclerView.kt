package com.example.taskat.core.ui.helper

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

object InitRecyclerView {
    fun <T> initWithAsyncDiffer(
        list: List<T>,
        asyncListDiffer: AsyncListDiffer<T>,
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        asyncListDiffer.submitList(list)
    }

    fun init(

        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager
    ) {

        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = layoutManager
        }

        recyclerView.adapter = adapter
    }


}