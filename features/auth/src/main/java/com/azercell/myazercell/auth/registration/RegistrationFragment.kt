package com.azercell.myazercell.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.azercell.myazercell.auth.R
import com.azercell.myazercell.auth.databinding.FragmentRegistrationBinding
import com.azercell.myazercell.core.base.BaseFragment
import com.azercell.myazercell.core.base.SessionHandler
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<RegistrationViewModel, FragmentRegistrationBinding, RegistrationContract.RegistrationState, RegistrationContract.RegistrationEffect, RegistrationContract.RegistrationEvent>() {

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegistrationBinding
        get() = FragmentRegistrationBinding::inflate

    override fun getViewModelClass() = RegistrationViewModel::class.java

    override val onViewInit: FragmentRegistrationBinding.() -> Unit = {
        handleTextChangeListeners()
        birthday.editText?.setOnClickListener {
            showDatePicker()
        }
        number.editText?.setOnFocusChangeListener { view, isFocused ->
            if (isFocused)
                number.editText?.hint = NUMBER_HINT
            else
                number.editText?.hint = ""
        }
        registerButton.setOnClickListener {
            postEvent {
                RegistrationContract.RegistrationEvent.StartRegistration(
                    binding.name.editText?.text.toString(),
                    binding.surname.editText?.text.toString(),
                    binding.birthday.editText?.text.toString(),
                    binding.number.editText?.text.toString()
                )
            }
        }
    }

    private fun handleTextChangeListeners() {
        withBinding {
            name.editText?.doOnTextChanged { text, start, before, count ->
                validateUI()
            }
            surname.editText?.doOnTextChanged { text, start, before, count ->
                validateUI()
            }
            birthday.editText?.doOnTextChanged { text, start, before, count ->
                validateUI()
            }
            number.editText?.doOnTextChanged { text, start, before, count ->
                validateUI()
            }
        }
    }

    private fun showDatePicker() {
        val datePicker: MaterialDatePicker<Long> = MaterialDatePicker
            .Builder
            .datePicker()
            .setTitleText(getString(R.string.registration_select_birth_date))
            .build()
        datePicker.show(childFragmentManager, this::class.java.canonicalName)
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
            val date: String = sdf.format(it)
            binding.birthday.editText?.setText(date)
        }
    }

    private fun validateUI() {
        postEvent {
            RegistrationContract.RegistrationEvent.ValidateBody(
                binding.name.editText?.text.toString(),
                binding.surname.editText?.text.toString(),
                binding.birthday.editText?.text.toString(),
                binding.number.editText?.text.toString()
            )
        }
    }

    override fun onStateUpdate(state: RegistrationContract.RegistrationState) {
        binding.registerButton.isEnabled = state.isUiValid
    }

    override fun onEffectUpdate(effect: RegistrationContract.RegistrationEffect) {
        when (effect) {
            is RegistrationContract.RegistrationEffect.OnRegisterSuccess -> {
                (requireActivity() as SessionHandler).onRegister(effect.authToken)
            }
        }
    }

    companion object {
        private const val NUMBER_HINT = "50 000 00 00"
        private const val DATE_FORMAT = "dd/MM/yyyy"
    }
}