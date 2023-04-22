package com.azercell.myazercell.auth.registration

import com.azercell.myazercell.core.base.BaseViewModel
import com.azercell.myazercell.core.base.UIEffect
import com.azercell.myazercell.core.base.UIEvent
import com.azercell.myazercell.core.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(

) : BaseViewModel<RegistrationContract.RegistrationState, RegistrationContract.RegistrationEffect, RegistrationContract.RegistrationEvent>() {
    override fun setInitialState() = RegistrationContract.RegistrationState(false)

    override fun handleEvents(event: RegistrationContract.RegistrationEvent) {
        when (event) {
            is RegistrationContract.RegistrationEvent.ValidateBody -> validateView(event)
            is RegistrationContract.RegistrationEvent.StartRegistration -> startRegistrationIntent()
        }
    }

    private fun startRegistrationIntent() {

    }

    private fun validateView(event: RegistrationContract.RegistrationEvent.ValidateBody) {
        val isValidUI = event.phoneNumber.isNotEmpty() && event.licence.isNotEmpty()
        postState {
            copy(isUiValid = isValidUI)
        }
    }
}

class RegistrationContract {
    sealed class RegistrationEvent : UIEvent {
        data class ValidateBody(val phoneNumber: String, val licence: String) : RegistrationEvent()
        data class StartRegistration(val phoneNumber: String, val licence: String) : RegistrationEvent()
    }

    data class RegistrationState(
        val isUiValid: Boolean = false,
    ) : UIState

    sealed class RegistrationEffect : UIEffect {

    }
}