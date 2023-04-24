package com.azercell.myazercell.transfers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.azercell.myazercell.core.base.BaseFragment
import com.azercell.myazercell.core.extensions.toast
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.transfers.databinding.FragmentTransfersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransfersFragment : BaseFragment<TransfersViewModel, FragmentTransfersBinding, TransfersContract.TransfersState, TransfersContract.TransfersEffect, TransfersContract.TransfersEvent>(),
    AdapterView.OnItemSelectedListener {

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTransfersBinding
        get() = FragmentTransfersBinding::inflate

    override fun getViewModelClass() = TransfersViewModel::class.java

    override val onViewInit: FragmentTransfersBinding.() -> Unit = {
        fromCard.onItemSelectedListener = this@TransfersFragment
        toCard.onItemSelectedListener = this@TransfersFragment
        amount.editText?.doAfterTextChanged {
            validateUI()
        }
        transfer.setOnClickListener {
            postEvent {
                TransfersContract.TransfersEvent.StartTransfer(
                    binding.fromCard.selectedItem,
                    binding.toCard.selectedItem,
                    binding.amount.editText?.text.toString()
                )
            }
        }
    }

    override fun onStateUpdate(state: TransfersContract.TransfersState) {
        val list = arrayListOf<Card?>()
        state.cardsList?.let {
            list.add(null)
            list.addAll(state.cardsList)
            val adapter = CardListAdapter(requireContext(), list)
            binding.fromCard.adapter = adapter
            binding.toCard.adapter = adapter
        }
    }

    override fun onEffectUpdate(effect: TransfersContract.TransfersEffect) {
        when (effect) {
            is TransfersContract.TransfersEffect.IsUiValid -> binding.transfer.isEnabled = effect.isValid
            TransfersContract.TransfersEffect.TransferSuccess -> {
                toast(getString(R.string.transfer_was_successful))
                findNavController().popBackStack()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        validateUI()
    }

    private fun validateUI() {
        postEvent {
            TransfersContract.TransfersEvent.OnCardSelect(
                binding.fromCard.selectedItem,
                binding.toCard.selectedItem,
                binding.amount.editText?.text.toString()
            )
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}