package com.azercell.myazercell.core.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    showToast(message, length)
}
