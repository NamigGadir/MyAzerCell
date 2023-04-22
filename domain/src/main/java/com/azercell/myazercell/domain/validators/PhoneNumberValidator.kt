package com.azercell.myazercell.domain.validators

import com.azercell.myazercell.domain.base.BaseValidator
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import javax.inject.Inject

class PhoneNumberValidator @Inject constructor() : BaseValidator<String> {

    companion object {
        const val LOCALE_AZ = "AZ"
    }

    override fun isValid(input: String): Boolean {
        val isValid = try {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val phoneNumber = phoneUtil.parse(input, LOCALE_AZ)
            phoneUtil.isValidNumber(phoneNumber)
        } catch (e: NumberParseException) {
            false
        }
        return isValid
    }
}