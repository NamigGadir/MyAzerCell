package com.azercell.myazercell.home.order_card

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.azercell.myazercell.core.base.BaseFragment
import com.azercell.myazercell.core.extensions.toast
import com.azercell.myazercell.home.databinding.FragmentOrderCardBinding

class OrderCardFragment : BaseFragment<OrderCardViewModel, FragmentOrderCardBinding, OrderCardContract.OrderCardState, OrderCardContract.OrderCardEffect, OrderCardContract.OrderCardEvent>(),
    AdapterView.OnItemSelectedListener {

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrderCardBinding
        get() = FragmentOrderCardBinding::inflate

    override fun getViewModelClass() = OrderCardViewModel::class.java

    override val onViewInit: FragmentOrderCardBinding.() -> Unit = {
        orderNewCardButton.setOnClickListener {
            postEvent {
                OrderCardContract.OrderCardEvent.StartOrderCard(
                    cardType.selectedItemPosition,
                    (cardCurrency.selectedItem as String),
                    binding.cardHolderName.editText?.text.toString()
                )
            }
        }
    }

    override fun onStateUpdate(state: OrderCardContract.OrderCardState) {
        binding.cardHolderName.editText?.setText("${state.userInfo?.surname} ${state.userInfo?.name}")
        state.cardNetworks?.let {
            showCardNetworks(it)
        }
        state.cardCurrencies?.let {
            showCardCurrencies(it)
        }
    }

    private fun showCardCurrencies(list: List<String>) {
        val spin = binding.cardCurrency
        spin.onItemSelectedListener = this
        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(requireContext(), R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
    }

    private fun showCardNetworks(list: List<String>) {
        val spin = binding.cardType
        spin.onItemSelectedListener = this
        val adapter: ArrayAdapter<*> = ArrayAdapter<Any?>(requireContext(), R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spin.adapter = adapter
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        postEvent {
            OrderCardContract.OrderCardEvent.SelectedCardType(position)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onEffectUpdate(effect: OrderCardContract.OrderCardEffect) {
        when (effect) {
            OrderCardContract.OrderCardEffect.OnCardTypeSelected -> {
                validateUI()
            }
            OrderCardContract.OrderCardEffect.OnCardAdded -> {
                toast(getString(com.azercell.myazercell.home.R.string.card_added_notification))
                findNavController().popBackStack()
            }
        }
    }

    private fun validateUI() {
        binding.orderNewCardButton.isEnabled = binding.cardType.selectedItem != null
    }

}
