package com.example.taskat.core.ui.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResourceHelper @Inject constructor(@ApplicationContext private val context: Context) {



    fun getStringFromRes(stringResId: Int): String {
        return try {
            context.getString(stringResId)

        } catch (ex: Exception) {
            ex.message.toString()

        }
    }
}