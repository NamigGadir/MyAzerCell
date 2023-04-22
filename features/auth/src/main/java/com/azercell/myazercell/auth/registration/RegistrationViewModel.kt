package com.azercell.myazercell.auth.registration

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import com.azercell.myazercell.core.util.Constants
import com.azercell.myazercell.domain.entity.remote.auth.RegisterRequest
import com.azercell.myazercell.domain.usecase.auth.RegisterUseCase
import com.azercell.myazercell.domain.validators.PhoneNumberValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val phoneNumberValidator: PhoneNumberValidator,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegistrationContract.RegistrationState, RegistrationContract.RegistrationEffect, RegistrationContract.RegistrationEvent>() {
    override fun setInitialState() = RegistrationContract.RegistrationState(false)

    override fun handleEvents(event: RegistrationContract.RegistrationEvent) {
        when (event) {
            is RegistrationContract.RegistrationEvent.ValidateBody -> validateView(event)
            is RegistrationContract.RegistrationEvent.StartRegistration -> startRegistrationIntent(event)
        }
    }

    private fun startRegistrationIntent(event: RegistrationContract.RegistrationEvent.StartRegistration) {
        registerUseCase.invokeRequest(
            RegisterRequest(
                event.name, event.surname, event.birth, Constants.NUMBER_PREFIX_AZ + event.phoneNumber
            )
        ) {
            postEffect {
                RegistrationContract.RegistrationEffect.OnRegisterSuccess(it.authToken)
            }
        }
    }

    private fun validateView(event: RegistrationContract.RegistrationEvent.ValidateBody) {
        val isValidUI = event.name.isNotEmpty()
                && event.surname.isNotEmpty()
                && event.birth.isNotEmpty()
                && event.phoneNumber.isNotEmpty()
                && phoneNumberValidator.isValid(event.phoneNumber)
        postState {
            copy(isUiValid = isValidUI)
        }
    }
}

class RegistrationContract {
    sealed class RegistrationEvent : UIEvent {
        data class ValidateBody(
            val name: String,
            val surname: String,
            val birth: String,
            val phoneNumber: String,
        ) : RegistrationEvent()

        data class StartRegistration(
            val name: String,
            val surname: String,
            val birth: String,
            val phoneNumber: String
        ) : RegistrationEvent()
    }

    data class RegistrationState(
        val isUiValid: Boolean = false,
    ) : UIState

    sealed class RegistrationEffect : UIEffect {
        data class OnRegisterSuccess(val authToken: String) : RegistrationEffect()
    }

}