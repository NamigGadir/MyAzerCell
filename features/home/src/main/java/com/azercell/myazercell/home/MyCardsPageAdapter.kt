package com.azercell.myazercell.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azercell.myazercell.core.extensions.maskCardNumber
import com.azercell.myazercell.domain.entity.enum.CardNetwork
import com.azercell.myazercell.domain.entity.remote.home.Card
import com.azercell.myazercell.home.databinding.CardsItemBinding
import com.azercell.myazercell.ui_toolkit.base.BaseAdapter

class MyCardsPageAdapter : BaseAdapter<Card, CardViewHolder>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.id == newItem.id
    },
    areContentsTheSame = { oldItem, newItem ->
        oldItem.equals(newItem)
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(CardsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItem(position: Int): Card {
        return currentList[position]
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id
    }

}

class CardViewHolder(private val binding: CardsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(card: Card) {
        binding.apply {
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
