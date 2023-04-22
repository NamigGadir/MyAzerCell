package com.azercell.myazercell.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.azercell.myazercell.auth.databinding.FragmentRegistrationBinding
import com.azercell.myazercell.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<RegistrationViewModel, FragmentRegistrationBinding, RegistrationContract.RegistrationState, RegistrationContract.RegistrationEffect, RegistrationContract.RegistrationEvent>() {

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegistrationBinding
        get() = FragmentRegistrationBinding::inflate

    override fun getViewModelClass() = RegistrationViewModel::class.java

    override val onViewInit: FragmentRegistrationBinding.() -> Unit = {

    }

    private fun validateUI() {
        postEvent {
            RegistrationContract.RegistrationEvent.ValidateBody(
                binding.number.editText?.text.toString(), binding.driverLicence.editText?.text.toString())
        }
    }

    override fun onStateUpdate(state: RegistrationContract.RegistrationState) {
        binding.nextButton.isEnabled = state.isUiValid
    }

    override fun onEffectUpdate(effect: RegistrationContract.RegistrationEffect) {
        when (effect) {
            else -> {}
        }
    }


}