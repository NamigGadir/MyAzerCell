package com.azercell.myazercell.domain.validators

import com.azercell.myazercell.domain.base.BaseValidator
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import javax.inject.Inject

class AmountValidator @Inject constructor() : BaseValidator<String> {

    companion object {
        const val REGEX = "-?\\d+(\\.\\d+)?"
    }

    override fun isValid(input: String): Boolean {
        return input.matches(REGEX.toRegex())
    }
}