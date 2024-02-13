package com.example.taskat.core.ui.helper

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

object CheckerVisibility {
    fun check(view: View) = view.isVisible
    fun check(viewGroup: ViewGroup) = viewGroup.isVisible
    fun setVisibility(view: View, visibilityMode: Int) {
        if (view.visibility != visibilityMode) {
            view.visibility = visibilityMode
        }
    }

    fun initEmptyList(
        emptyLayout: ViewGroup,
        headerRecyclerView: ViewGroup,
        recyclerView: RecyclerView
    ) {
        if (!check(emptyLayout)) {
            setVisibility(emptyLayout, View.VISIBLE)
        }
        if (check(headerRecyclerView)) {
            setVisibility(headerRecyclerView, View.GONE)
        }
        if (check(recyclerView)) {
            setVisibility(recyclerView, View.GONE)
        }
    }

    fun initEmptyList(emptyLayout: ViewGroup, recyclerView: RecyclerView) {
        if (!check(emptyLayout)) {
            setVisibility(emptyLayout, View.VISIBLE)
        }

        if (check(recyclerView)) {
            setVisibility(recyclerView, View.GONE)
        }
    }

    fun initNonEmptyList(
        emptyLayout: ViewGroup,
        headerRecyclerView: ViewGroup,
        recyclerView: RecyclerView
    ) {
        if (check(emptyLayout)) {
            setVisibility(emptyLayout, View.GONE)
        }
        if (!check(headerRecyclerView)) {
            setVisibility(headerRecyclerView, View.VISIBLE)
        }
        if (!check(recyclerView)) {
            setVisibility(recyclerView, View.VISIBLE)
        }
    }

    fun initNonEmptyList(emptyLayout: ViewGroup, recyclerView: RecyclerView) {
        if (check(emptyLayout)) {
            setVisibility(emptyLayout, View.GONE)
        }

        if (!check(recyclerView)) {
            setVisibility(recyclerView, View.VISIBLE)
        }
    }

}