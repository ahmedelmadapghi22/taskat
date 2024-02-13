package com.example.taskat.domain.util

private const val EGYPT = "+20"
private const val EMIRATES = "+971"

object PreConditionPhone {
    private fun checkLengthPhone(phone: String, requiredLength: Int): Boolean {
        return phone.length == requiredLength

    }

    fun checkCountryCode(countryCode: String, phone: String): String {
        when (countryCode) {
            EGYPT -> {
                if (!checkLengthPhone(phone, 11))
                    return "Phone must be 11 numbers"


            }

            EMIRATES -> {
                return if (!checkLengthPhone(phone, 9))
                    "Phone must be 9 numbers"
                else
                    ""
            }

        }
        return ""
    }

    fun removeZeroEGY(phone: String): String {
        val newPhone = phone.subSequence(1, phone.length).toString()
        if (phone.length == 11) {
            return if (phone.startsWith("0"))
                newPhone
            else
                phone
        }
        return phone
    }

    fun getCountryCode(phone: String):String {
        return if (phone.startsWith(EGYPT)) {
            EGYPT
        } else {
            phone.subSequence(0,3).toString()
        }
    }
    fun getPhoneNumber(fullPhone: String):String {
        return if (fullPhone.startsWith(EGYPT)) {
            fullPhone.subSequence(3,fullPhone.length).toString()
        } else {
            fullPhone.subSequence(4,fullPhone.length).toString()
        }
    }
}