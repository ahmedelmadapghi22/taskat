package com.example.taskat.core.ui.helper

import androidx.core.view.isVisible
import com.example.taskat.databinding.LoadingLayoutBinding

object VisibilityLoadingLayout {
    fun setVisibility(loadingLayoutBinding: LoadingLayoutBinding, isVisible: Boolean) {
        loadingLayoutBinding.root.isVisible = isVisible
    }
}