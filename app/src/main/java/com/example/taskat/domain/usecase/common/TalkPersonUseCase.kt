package com.example.taskat.domain.usecase.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TalkPersonUseCase @Inject constructor(@ApplicationContext private val context: Context) {

    operator fun invoke(phoneNumber: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent)
    }

}