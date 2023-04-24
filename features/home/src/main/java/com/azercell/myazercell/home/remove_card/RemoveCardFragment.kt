package com.azercell.myazercell.home.remove_card

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.azercell.myazercell.core.base.BaseFragment
import com.azercell.myazercell.core.base.BaseWarningDialog
import com.azercell.myazercell.core.base.baseWarningDialog
import com.azercell.myazercell.core.extensions.maskCardNumber
import com.azercell.myazercell.core.extensions.toast
import com.azercell.myazercell.domain.entity.enum.CardNetwork
import com.azercell.myazercell.home.HomeContract
import com.azercell.myazercell.home.databinding.FragmentOrderCardBinding
import com.azercell.myazercell.home.databinding.FragmentRemoveCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemoveCardFragment : BaseFragment<RemoveCardViewModel, FragmentRemoveCardBinding, RemoveCardContract.RemoveCardState, RemoveCardContract.RemoveCardEffect, RemoveCardContract.RemoveCardEvent>() {

    private val arguments by navArgs<RemoveCardFragmentArgs>()

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRemoveCardBinding
        get() = FragmentRemoveCardBinding::inflate

    override fun getViewModelClass() = RemoveCardViewModel::class.java

    override val onViewInit: FragmentRemoveCardBinding.() -> Unit = {
        postEvent {
            RemoveCardContract.RemoveCardEvent.LoadCardInfo(arguments.cardId)
        }
        removeCardButton.setOnClickListener {
            baseWarningDialog {
                subTitle {
                    getString(com.azercell.myazercell.home.R.string.are_you_sure_delete)
                }
                onAccept = {
                    postEvent {
                        RemoveCardContract.RemoveCardEvent.OnCardDelete(arguments.cardId)
                    }
                }
            }.show(childFragmentManager, BaseWarningDialog::class.java.canonicalName)
        }
    }

    override fun onStateUpdate(state: RemoveCardContract.RemoveCardState) {
        state.userCard?.let { card ->
            withBinding {
                cardBalance.text = binding.root.context.getString(com.azercell.myazercell.ui_toolkit.R.string.card_balance, card.cardBalance, card.cardCurrency)
                cardNetworkType.setImageResource(
                    when (card.cardNetwork) {
                        CardNetwork.VISA -> com.azercell.myazercell.ui_toolkit.R.drawable.ic_visa_logo
                        CardNetwork.MC -> com.azercell.myazercell.ui_toolkit.R.drawable.ic_mastercard_logo
                    }
                )
                cardHolderName.text = card.cardHolderName
                cardPan.text = card.cardNumber.maskCardNumber()
                cardExpireDate.text = card.expireDate
            }
        }
    }

    override fun onEffectUpdate(effect: RemoveCardContract.RemoveCardEffect) {
        when (effect) {
            RemoveCardContract.RemoveCardEffect.OnCardRemoved -> {
                toast(getString(com.azercell.myazercell.home.R.string.card_removed_notification))
                findNavController().popBackStack()
            }
        }
    }

}
