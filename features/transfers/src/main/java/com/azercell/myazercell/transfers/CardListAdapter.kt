package com.azercell.myazercell.transfers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.azercell.myazercell.core.extensions.maskCardNumber
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.transfers.databinding.CardSelectorHeaderBinding
import com.azercell.myazercell.transfers.databinding.CardTypeSelectorItemBinding

class CardListAdapter(private val context: Context, private val cardsList: ArrayList<Card?>) : ArrayAdapter<Card?>(context, 0, cardsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return if (position == 0) {
            CardSelectorHeaderBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            val binding = CardTypeSelectorItemBinding.inflate(LayoutInflater.from(context), parent, false)
            getItem(position)?.let { card ->
                setCardInfo(binding, card)
            }
            binding
        }.root
    }

    private fun setCardInfo(view: CardTypeSelectorItemBinding, card: Card) {
        view.cardHolder.text = card.cardHolderName
        view.cardNumber.text = card.cardNumber.maskCardNumber()
        view.cardBalance.text = view.root.context.getString(com.azercell.myazercell.ui_toolkit.R.string.card_balance, card.cardBalance, card.cardCurrency)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return if (position == 0) {
            CardSelectorHeaderBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            val binding = CardTypeSelectorItemBinding.inflate(LayoutInflater.from(context), parent, false)
            getItem(position)?.let { card ->
                setCardInfo(binding, card)
            }
            binding
        }.root
    }

    override fun getItem(position: Int): Card? {
        if (position == 0) {
            return null
        }
        return super.getItem(position)
    }
}