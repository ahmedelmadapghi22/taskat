package com.example.taskat.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Specialist(
    var id: Int = 0,
    var name: String,
    var phone: String,
    var evaluation: Float,
    var notes: String
) : Parcelable
