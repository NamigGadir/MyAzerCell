package com.azercell.myazercell.core.extensions

fun String.maskCardNumber(mask: String): String? {
    var index = 0
    val maskedNumber = StringBuilder()
    for (element in mask) {
        when (element) {
            '#' -> {
                maskedNumber.append(this[index])
                index++
            }
            '*' -> {
                maskedNumber.append(element)
                index++
            }
            else -> {
                maskedNumber.append(element)
            }
        }
    }
    return maskedNumber.toString()
}